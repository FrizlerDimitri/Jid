package de.oth.hashing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Hashable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6167685795334824479L;

	
	/**
	 * 
	 * @return SHA-1 Hash String of object that used this method
	 */
	public String HashMe() {

		return dataToHash(this);
	}

	
	
	/**
	 * 
	 * @param o Object that should be Hashed
	 * @return SHA-1 Hash String of the given Object 
	 */
	public String dataToHash(Object o) {

		final String algorithm = "SHA-1";

		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);

			try {
				byte[] bytedata = dataToBytes(o);
				byte[] data = md.digest(bytedata);
				
				
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < data.length; i++) {

					sb.append(String.format("%02x", data[i]));

				}

				
				String HashString = sb.toString();
				
				
				return HashString;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	
	/**
	 * 
	 * @param data that should convert in a byte array
	 * @return the byte array of the given data
 	 * @throws IOException
	 */
	private byte[] dataToBytes(Object data) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(data);
		return out.toByteArray();
	}

}
