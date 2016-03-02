package com.necl.core.daoImpl;

import com.necl.core.dao.FinChargeCodeDao;
import com.necl.core.model.FinanceChargeCode;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FinChargeCodeDaoImpl implements FinChargeCodeDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<FinanceChargeCode> findAll() {
        @SuppressWarnings("unchecked")
        List<FinanceChargeCode> financeChargeCodeList = getSession()
                .createCriteria(FinanceChargeCode.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return financeChargeCodeList;
    }

    @Override
    public List<FinanceChargeCode> findChargeCodeAsType(String idBranch, String typeItem) {

        if (typeItem.equals("ADV")) {
            typeItem = "PTC";
        }
        String sql = "SELECT     tblMaster_FinChargeCode.ChargeCodeId, tblMaster_FinChargeCode.BranchID, tblMaster_FinChargeCode.ItemFIN, tblMaster_FinChargeCode.Description, \n"
                + "tblMaster_FinChargeCode.Stop, tblMaster_FinChargeCode.Update_Time, tblMaster_FinChargeCode.Acc_Code, tblMaster_FinChargeCode.Stop AS Expr1\n"
                + "FROM         tblMaster_FinChargeCodeType INNER JOIN\n"
                + "tblMaster_FinChargeCode ON tblMaster_FinChargeCodeType.BranchID = tblMaster_FinChargeCode.BranchID AND \n"
                + "tblMaster_FinChargeCodeType.ItemFIN = tblMaster_FinChargeCode.ItemFIN\n"
                + "WHERE    ((tblMaster_FinChargeCode.BranchID = '00') AND (tblMaster_FinChargeCodeType.TypeItem = '"+typeItem+"') AND (tblMaster_FinChargeCode.Stop = 'False')) OR\n"
                + "(tblMaster_FinChargeCode.BranchID = '"+idBranch+"') AND (tblMaster_FinChargeCodeType.TypeItem = '"+typeItem+"') AND (tblMaster_FinChargeCode.Stop = 'False') \n"
                + "ORDER BY tblMaster_FinChargeCode.Description";

        SQLQuery query = getSession().createSQLQuery(sql);
        query.addEntity(FinanceChargeCode.class);
        List results = query.list();
        return results;
    }

    /*
    
    
     */
    @Override
    public FinanceChargeCode findById(Long id) {
        return (FinanceChargeCode) getSession().get(FinanceChargeCode.class, id);
    }
}
