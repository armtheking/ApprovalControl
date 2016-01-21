/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.necl.core.function.RunConfigNumber;
import com.necl.core.model.ConfigSystem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author C13.207
 */
public class RunConfigFunctionTest {

    ConfigSystem configSystem;
    
    public RunConfigFunctionTest() {
        configSystem = new ConfigSystem();
    }

    @Before
    public void setUp() {

        configSystem.setConfigDesc("PETTYCASH");
        configSystem.setConfigText("1511/");
        configSystem.setConfigPrefix("PTC");
        configSystem.setConfigRun("0001");
        configSystem = RunConfigNumber.getNumberTicket(configSystem);

    }

    @Test
    public void TestConfigMemoShouldBeNumberUse() {
        assertEquals("PTC1511/0001", configSystem.getConfigMemo());
    }
    
    @Test
    public void TestConfigRunShouldPlusOne() {
        assertEquals("0002", configSystem.getConfigRun());
    }
    
    
    
}
