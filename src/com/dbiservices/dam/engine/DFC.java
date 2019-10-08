package com.dbiservices.dam.engine;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.documentum.fc.client.DfClient;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfTypedObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;



public class DFC {

	public static IDfSession getSession(String docbase, String host, int port, String user, String password) throws DfException, GeneralSecurityException, IOException {
		IDfLoginInfo loginInfoObj = new DfLoginInfo();
		loginInfoObj.setUser(user);
		//loginInfoObj.setPassword(Utils.decryptPassword(password));
		loginInfoObj.setPassword(password);

		IDfClient client = new DfClient();

		IDfTypedObject cfg = client.getClientConfig();
		cfg.setString("primary_host", host);
		cfg.setInt("primary_port", port);

		return client.newSession(docbase, loginInfoObj);
	}
	
//	public static IDfSession getSession(Docbase config) throws DfException, GeneralSecurityException, IOException {
//		return getSession(config.name, config.host, config.port, config.user, Utils.decryptPassword(config.password));
//	}
	
	public static synchronized IDfCollection executeDQL(IDfSession session, String dql) throws DfException{
		IDfQuery query = new DfQuery();
		query.setDQL(dql);
		return query.execute(session, 1);
	}
}
