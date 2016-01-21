/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.necl.core.function.CalculateCost;
import com.necl.core.model.TicketDetail;
import com.necl.core.model.TicketHeader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author C13.207
 */
public class CalculateCostTest {

    TicketHeader ticketHeader;

    public CalculateCostTest() {
        ticketHeader = new TicketHeader();
    }

    @Before
    public void setUp() {

        List<TicketDetail> detailList = new ArrayList<>();
        TicketDetail ticketDetail1 = new TicketDetail();
        TicketDetail ticketDetail2 = new TicketDetail();
        ticketDetail1.setAmount(new BigDecimal("2000"));
        ticketDetail2.setAmount(new BigDecimal("333"));

        detailList.add(ticketDetail1);
        detailList.add(ticketDetail2);

        ticketHeader.setTicketdetail(detailList);
    }

    @Test
    public void TestCalculateTotalCost() {
        BigDecimal expected = new BigDecimal("2333");
        BigDecimal actual = CalculateCost.getTotalCost(ticketHeader);
        assertEquals(expected, actual);
    }
}
