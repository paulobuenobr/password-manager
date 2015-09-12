
package br.com.paulobueno.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.paulobueno.model.Password;

/**
 * Classe que realiza a persistência dos conjuntos de senhas para o caso de queda
 * do servidor
 * 
 * @author paulo.bueno
 *
 */

// TODO melhorar para prever alteração na entity password (um novo deploy deve apagar os .ser, p.ex.)
public class PasswordPersistence {
	
	public void persistNormalPasswordSet(Set<Password> normalPasswordSet, String path) throws Exception {
		OutputStream fos = new FileOutputStream(path+"normalPasswordSet.ser");
		OutputStream bos = new BufferedOutputStream(fos);
		ObjectOutput oos = new ObjectOutputStream(bos);
		oos.writeObject(normalPasswordSet);
		oos.close();
	}

	public void persistPreferentialPasswordSet(Set<Password> preferentialPasswordSet, String path) throws Exception {
		OutputStream fos = new FileOutputStream(path+"preferentialPasswordSet.ser");
		OutputStream bos = new BufferedOutputStream(fos);
		ObjectOutput oos = new ObjectOutputStream(bos);
		oos.writeObject(preferentialPasswordSet);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public LinkedHashSet<Password> loadNormalPasswordSet(String path) throws Exception {
		File fi = new File(path+"normalPasswordSet.ser");
		LinkedHashSet<Password> normalPasswordSet = null;
		if (fi.exists()) {
			InputStream fis = new FileInputStream(fi);
			InputStream bis = new BufferedInputStream(fis);
			ObjectInput ois = new ObjectInputStream(bis);
			try {
				normalPasswordSet = (LinkedHashSet<Password>) ois.readObject();
			} finally {
				ois.close();
			}
		}
		if (normalPasswordSet==null) {
			normalPasswordSet = new LinkedHashSet<>();
		}
		return normalPasswordSet;
	}

	@SuppressWarnings("unchecked")
	public LinkedHashSet<Password> loadPreferentialPasswordSet(String path) throws Exception {
		File fi = new File(path+"preferentialPasswordSet.ser");
		LinkedHashSet<Password> preferentialPasswordSet = null;
		if (fi.exists()) {
			InputStream fis = new FileInputStream(fi);
			InputStream bis = new BufferedInputStream(fis);
			ObjectInput ois = new ObjectInputStream(bis);
			try {
				preferentialPasswordSet = (LinkedHashSet<Password>) ois.readObject();
			} finally {
				ois.close();
			}
		}
		if (preferentialPasswordSet==null) {
			preferentialPasswordSet = new LinkedHashSet<>();
		}
		return preferentialPasswordSet;
	}

}
