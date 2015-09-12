package br.com.paulobueno.mb;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.paulobueno.model.Password;
import br.com.paulobueno.service.PasswordPersistence;

/**
 * Managed Bean
 * 
 * @author paulo.bueno
 *
 */
@ManagedBean
@SessionScoped
public class PasswordMB {

	private PasswordPersistence passwordPersistence;
	
	private Password newPassword;
	
	private int preferentialPasswordCount;
	private int normalPasswordCount;
	private LinkedHashSet<Password> normalPasswordSet;
	private LinkedHashSet<Password> preferentialPasswordSet;
	private Password nextPassword;
	private String persistencePath;

	public PasswordMB() throws Exception {
		loadPasswords();
	}
	
	private void loadPasswords() throws Exception {
		persistencePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+"/resources/persistence/";
		passwordPersistence = new PasswordPersistence();
		normalPasswordSet = passwordPersistence.loadNormalPasswordSet(persistencePath);
		normalPasswordCount = getLastNormalPasswordNumber();
		preferentialPasswordSet = passwordPersistence.loadPreferentialPasswordSet(persistencePath);
		preferentialPasswordCount = getLastPreferentialPasswordNumber();
		Collections.synchronizedSet(normalPasswordSet);
		Collections.synchronizedSet(preferentialPasswordSet);
	}
	
	public int getPreferentialPasswordCount() {
		return preferentialPasswordCount;
	}

	public int getNormalPasswordCount() {
		return normalPasswordCount;
	}

	public void generateNormalPassword(ActionEvent actionEvent) throws Exception {
		synchronized (normalPasswordSet) {
			normalPasswordCount++;
			if (normalPasswordCount>99999) {
				restartNormalPassword();
				normalPasswordCount=1;
			}
			newPassword = new Password();
			newPassword.setNumber(normalPasswordCount);
			newPassword.setType(Password.NORMAL_PASSWORD);
			newPassword.setCode(String.format(Password.NORMAL_FORMAT, normalPasswordCount));
			normalPasswordSet.add(newPassword);
			persistNormalPasswordSet();
		}
	}

	public void generatePreferentialPassword(ActionEvent actionEvent) throws Exception {
		synchronized (preferentialPasswordSet) {
			preferentialPasswordCount++;
			if (preferentialPasswordCount>99999) {
				restartPreferentialPassword();
				preferentialPasswordCount=1;
			}
			newPassword = new Password();
			newPassword.setNumber(preferentialPasswordCount);
			newPassword.setType(Password.PREFERENTIAL_PASSWORD);
			newPassword.setCode(String.format(Password.PREFERENTIAL_FORMAT, preferentialPasswordCount));
			preferentialPasswordSet.add(newPassword);
			persistPreferentialPasswordSet();
		}
	}

	public LinkedHashSet<Password> getNormalPasswordSet() {
		return normalPasswordSet;
	}

	public LinkedHashSet<Password> getPreferentialPasswordSet() {
		return preferentialPasswordSet;
	}

	public Password getNewPassword() {
		return newPassword;
	}
	
	public void selectNextPassword() throws Exception {
		// Itera sobre as senhas preferenciais inicialmente
		synchronized (preferentialPasswordSet) {
			Iterator<Password> ipp=preferentialPasswordSet.iterator();
		    while(ipp.hasNext()){
		        Password p=ipp.next();
		        if (!p.isCalled()) {
		        	nextPassword = p;
		        	nextPassword.setCalled(true);
		        	persistPreferentialPasswordSet();
		        	return;
		        }
		    }
		}
		// Se necessário, itera sobre as senhas normais
		synchronized (normalPasswordSet) {
			Iterator<Password> npp=normalPasswordSet.iterator();
		    while(npp.hasNext()){
		        Password p=npp.next();
		        if (!p.isCalled()) {
		        	nextPassword = p;
		        	nextPassword.setCalled(true);
		        	persistNormalPasswordSet();
		        	return;
		        }
		    }
		}

	}

	public Password getNextPassword() {
		return nextPassword;
	}
	
	private void persistNormalPasswordSet() throws Exception {
		passwordPersistence.persistNormalPasswordSet(normalPasswordSet, persistencePath);
	}
	
	private void persistPreferentialPasswordSet() throws Exception {
		passwordPersistence.persistPreferentialPasswordSet(preferentialPasswordSet, persistencePath);
	}
	
	private int getLastNormalPasswordNumber() {
		int last=0;
		synchronized (normalPasswordSet) {
			Iterator<Password> ipp=normalPasswordSet.iterator();
		    while(ipp.hasNext()){
		        Password p=ipp.next();
		        if (!p.isCalled()) {
		        	last=p.getNumber();
		        }
		    }
		}
		return last;
	}
	
	private int getLastPreferentialPasswordNumber() {
		int last=0;
		synchronized (preferentialPasswordSet) {
			Iterator<Password> ipp=preferentialPasswordSet.iterator();
		    while(ipp.hasNext()){
		        Password p=ipp.next();
		        if (!p.isCalled()) {
		        	last=p.getNumber();
		        }
		    }
		}
		return last;
	}
	
	public void restartAllPasswords() throws Exception {
		restartNormalPassword();
		restartPreferentialPassword();
		nextPassword=null;
		newPassword=null;
	}
	
	private void restartNormalPassword() throws Exception {
		normalPasswordCount=0;
		synchronized (normalPasswordSet) {
			normalPasswordSet.clear();
			persistNormalPasswordSet();
		}
	}

	private void restartPreferentialPassword()  throws Exception {
		preferentialPasswordCount=0;
		synchronized (preferentialPasswordSet) {
			preferentialPasswordSet.clear();
			persistPreferentialPasswordSet();
		}
	}
	
}