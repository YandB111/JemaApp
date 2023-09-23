/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.entities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElutionOrderIdGenerator implements IdentifierGenerator {
	
    protected Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		String prefix = "EORD";
		Connection connection = session.connection();

		try(Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select nextval('elution_order_id_seq')");) {
			if (rs.next()) {
				int id = rs.getInt(1) + 1000;
				String generatedId = prefix + Integer.toString(id);
				logger.info("Generated Id: {}", generatedId);
				return generatedId;
			}
		} catch (SQLException e) {
		    	logger.info("-----------error--------");
			e.printStackTrace();
		}
		return null;
	}


}
