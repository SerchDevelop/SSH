package com.mx.nfs;

import java.io.File;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class CopyTest {
	
	private static final String userTomcat = "bitnami";
	private static final String userOracle = "opc";
	private static final String passwordTomcat = "developIndra.";
	private static final String passwordOracle = "developIndra.";
//	private static final String passwordTomcat = "nextelRetail.";
//	private static final String passwordOracle = "productionIndra.";
//	private static final String hostTomcat = "129.152.42.86";
//	private static final String hostTomcat = "160.34.102.100";
//	private static final String hostOracle = "160.34.102.36";
	private static final String hostTomcat = "129.152.42.177";
	private static final String hostOracle = "129.152.132.37";
	private static final int port = 22;
	private static final String remoteFileTomcat = "//opt//ingenLOAD//";
	private static final String remoteFileOracle = "/opt/InGEN-LOAD/datos/data/";

	static Session sessionTomcat = null;
	static Session sessionOracle = null;
	static ChannelSftp sftpChannelTomcat = null;
	static ChannelSftp sftpChannelOracle = null;
	
	public static void main(String args[]) {
					
			CopyTest sshReadFile = new CopyTest();
			sshReadFile.conectar();
	}
	
	public void conectar(){
		
	    try {

	    	JSch jsch = new JSch();
	        String absoluteFilePathPrivatekey = "/";
	        File tmpFileObject = new File("./src/resources/privateKeyTomcatDev177");
	        if (tmpFileObject.exists() && tmpFileObject.isFile()){
	          absoluteFilePathPrivatekey = tmpFileObject.getAbsolutePath();
	        }
	        jsch.addIdentity(absoluteFilePathPrivatekey, passwordTomcat);
//	        jsch.addIdentity(absoluteFilePathPrivatekey, "nextelRetail.");
	        
	        // Tomcat
	    	sessionTomcat = jsch.getSession(userTomcat, hostTomcat, port);
            sessionTomcat.setPassword(passwordTomcat);
            
            sessionTomcat.setConfig("StrictHostKeyChecking", "no");
	        System.out.println("\nEstablishing Connection...");
	        
	        sessionTomcat.connect();
            System.out.println("Connection established.");
	        System.out.println("Crating SFTP Channel.");
	        
	        sftpChannelTomcat = (ChannelSftp) sessionTomcat.openChannel("sftp");
	        sftpChannelTomcat.connect();
	        System.out.println("SFTP Channel created.\n");
	        
	        // Oracle
	        String absoluteFilePathPrivatekeyOracle = "/";
	        File tmpFileObjectOracle = new File("./src/resources/privateKeyOracleDev");
	        if (tmpFileObjectOracle.exists() && tmpFileObjectOracle.isFile()){
	        	absoluteFilePathPrivatekeyOracle = tmpFileObjectOracle.getAbsolutePath();
	        }
	        jsch.addIdentity(absoluteFilePathPrivatekeyOracle, passwordOracle);

	    	sessionOracle = jsch.getSession(userOracle, hostOracle, port);
            sessionOracle.setPassword(passwordOracle);
            
            sessionOracle.setConfig("StrictHostKeyChecking", "no");
	        System.out.println("\nEstablishing Connection Oracle...");
	        
	        sessionOracle.connect();
            System.out.println("Connection established Oracle.");
	        System.out.println("Crating SFTP Channel Oracle.");
	        
	        sftpChannelOracle= (ChannelSftp) sessionOracle.openChannel("sftp");
	        sftpChannelOracle.connect();
	        System.out.println("SFTP Channel created Oracle.\n");

	        Vector<ChannelSftp.LsEntry> list = sftpChannelTomcat.ls(remoteFileTomcat);
System.out.println("sftpChannelTomcat :: " + sftpChannelTomcat);
	        for (ChannelSftp.LsEntry oListItem : list) { // Iterate objects in the list to get file/folder names.
	        	System.out.println("oListItem.getFilename() :: " + oListItem.getFilename());
        		if(oListItem.getFilename().contains(".xml")){
					System.out.println("Preparando copy...");
					File f = new File(oListItem.getFilename());
					System.out.println("Se obtiene archivo :: " + oListItem.getFilename());
//					sftpChannelOracle.put(sftpChannelTomcat.get(remoteFileTomcat + oListItem.getFilename()), remoteFileOracle + f.getName(), ChannelSftp.OVERWRITE);
 //           		sftpChannelTomcat.put(remoteFileTomcat + "copy/" + oListItem.getFilename(),  remoteFileTomcat + oListItem.getFilename());//remoteFileTomcat + "copy/" + oListItem.getFilename());
					System.out.println("Se copio archivo :: " + oListItem.getFilename());
//					sftpChannelTomcat.rm(remoteFileTomcat + oListItem.getFilename());
//					System.out.println("Se elimino archivo :: " + remoteFileTomcat + oListItem.getFilename());
        		}
	        }
	        
        } catch(Exception error) {
        	sessionTomcat = null;
        	sftpChannelTomcat = null;
        	sftpChannelOracle = null;
        	sessionOracle = null;
        	System.err.print("Error al establecer la conexion :: \n" + error);
        }
    }
	    
}
