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
        
        if(typeItem.equals("ADV")){
            typeItem="PTC";
        }        
        String sql = "SELECT dbApproval.dbo.tblMaster_FinChargeCode.ChargeCodeId"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.BranchID"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.ItemFIN"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.Description"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.Stop"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.Update_Time"
                + ", dbApproval.dbo.tblMaster_FinChargeCode.Acc_Code"
                + " FROM tblMaster_FinChargeCodeType"
                + " INNER JOIN dbApproval.dbo.tblMaster_FinChargeCode"
                + " ON tblMaster_FinChargeCodeType.BranchID = dbApproval.dbo.tblMaster_FinChargeCode.BranchID"
                + " AND tblMaster_FinChargeCodeType.ItemFIN = dbApproval.dbo.tblMaster_FinChargeCode.ItemFIN"
                + " WHERE     (dbApproval.dbo.tblMaster_FinChargeCode.BranchID = '"+idBranch+"')"
                + " AND (tblMaster_FinChargeCodeType.TypeItem = '"+typeItem+"')"
                + "ORDER BY dbApproval.dbo.tblMaster_FinChargeCode.Description ASC";
 
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
