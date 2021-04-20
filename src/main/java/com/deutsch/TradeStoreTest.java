package com.deutsch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class TradeStoreTest {

        TradeService tsObj = new TradeService();
        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

        @Test
        void testIfTradeEmpty() {

            assertTrue(tsObj.checkIfTradeEmpty());
        }

        @Test
        void testAddTrade() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");
            TradeModel t1 = new TradeModel("T1", 1, "CP-1", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t1);
            //Checking if
            assertEquals(1, tf.allTrade.size());
        }
        //Check for Version

        //Check if Version is high the list will be updated
        //T2	2	CP-1	B1	20/05/2021	today date	N
        //T2	5	CP-5	B1	20/05/2021	today date 	N
        @Test
        @Order(1)
        void testVersionHigh() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");
            TradeModel t2 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t2);

            //Changing Version as 3 and Changing Counter-Party ID to CP-4
            TradeModel t3 = new TradeModel("T2", 5, "CP-4", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t3);
            assertEquals("CP-4", tsObj.allTrade.get("T2").getCounterPartId());
        }

        //Check if Version is same the list will be updated
        //T1	1	CP-1	B1	20/05/2020	today date  N
        //T1	1	CP-2	B1	20/05/2020	today date	N
        @Test
        @Order(2)
        void testVersionSame() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");
            //Same Version as before and Changing Counter-Party ID to CP-2
            TradeModel t4 = new TradeModel("T1", 1, "CP-2", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t4);
            assertEquals("CP-2", tsObj.allTrade.get("T1").getCounterPartId());
        }

        //Check if Version is low the trade will be rejected
        //T3	5	CP-3	B1	20/05/2014	today date	N
        //T3	1	CP-2	B1	20/05/2014	today date	N
        @Test
        @Order(3)
        void testVersionLow() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");

            TradeModel t5 = new TradeModel("T3", 5, "CP-3", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t5);
            int sizeofList = tsObj.allTrade.size();
            //Now Adding Another List
            TradeModel t6 = new TradeModel("T3", 1, "CP-2", "B1", maturityDate, todaysDate, 'N');

            assertThrows(Exception.class, () -> tsObj.addTrade(t6), "1 is less than 5");

        }

        //Check if maturity Date is greater than todays date the trade is added
        //T4	5	CP-3	B1	20/05/2021	today date	N
        @Test
        @Order(4)
        void testMaturityGreater() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");

            TradeModel t7 = new TradeModel("T4", 5, "CP-4", "B3", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t7);

            assertEquals(t7, tsObj.allTrade.get("T4"));

        }

        //Check if maturity Date is lower than todays date the Trade will not be added
        //T5  5  CP-3  B1  20/05/2020   today date  N
        @Test
        @Order(5)
        void testMaurityLower() throws Exception {
            Date maturityDate = sd.parse("20/05/2020");
            TradeModel t8 = new TradeModel("T5", 1, "CP-4", "B3", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t8);
            assertNull(tsObj.allTrade.get("T5"));
        }

        //Check if Version is Same and date is lower the trade is not updated
        //T6	1	CP-2	B1	20/05/2021	today date N
        //T6	1	CP-2	B1	20/05/2020	today date	N
        @Test
        @Order(6)
        void testMaturityLowerVersionSame() throws Exception {

            Date maturityDate1 = sd.parse("20/05/2021");
            TradeModel t9 = new TradeModel("T6", 1, "CP-2", "B1", maturityDate1, todaysDate, 'N');
            tsObj.addTrade(t9);
            Date maturityDate = sd.parse("20/05/2021");
            TradeModel t10 = new TradeModel("T6", 1, "CP-2", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t10);
            assertEquals(maturityDate1, tsObj.allTrade.get("T6").getMaturityDate());
        }

        //Check if Maturity Date is Same as Todays Date the list will be added
        //T7 7  CP-5  B4  todaysDate  todaysDate  N

        @Test
        @Order(7)
        void testSameMaturity() throws Exception {
            Date todaysDate = Calendar.getInstance().getTime();
            TradeModel t11 = new TradeModel("T7", 7, "CP-5", "B4", todaysDate, todaysDate, 'N');
            tsObj.addTrade(t11);
            assertNotNull(tsObj.allTrade.get("T7"));
        }

        //Check if version is high but maturity date is low the trade will be regected
        //T8 1  CP-3  B1  20/05/2021  todaysDate  N
        //T8 5  CP-3  B1  20/05/2020  todaysDate  N
        @Test
        @Order(8)
        void testMaturitySameVersionMaturityLow() throws Exception {

            Date maturityDate = sd.parse("20/05/2021");

            TradeModel t12 = new TradeModel("T8", 1, "CP-3", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t12);
            maturityDate = sd.parse("20/05/2020");
            //Now Adding Another List
            TradeModel t13 = new TradeModel("T8", 5, "CP-2", "B1", maturityDate, todaysDate, 'N');
            assertEquals(1, tsObj.allTrade.get("T8").getVersion());

        }

        //Check if both version and maturity low the trade will not be added
        //T8 1  CP-3  B1  20/05/2021  todaysDate  N
        //T8 5  CP-3  B1  20/05/2020  todaysDate  N1
        @Test
        @Order(9)
        void testVersionAndMaturityLow() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");

            TradeModel t14 = new TradeModel("T9", 5, "CP-3", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t14);

            maturityDate = sd.parse("20/05/2020");
            //Now Adding Another List
            TradeModel t15 = new TradeModel("T9", 1, "CP-2", "B1", maturityDate, todaysDate, 'N');
            assertThrows(Exception.class, () -> tsObj.addTrade(t15), "1 is less than 5");

        }

        //Check If Maturity Date is Expired it will update the Expired Flag
        @Test
        @Order(10)
        void testExpiry() throws ParseException {
            Date maturityDate = sd.parse("20/05/2020");
            TradeModel t16 = new TradeModel("T10", 6, "CP-4", "B1", maturityDate, todaysDate, 'N');
            tsObj.allTrade.put("T10", t16); // hardcoded as it need to be tested and the conditio is false
            tsObj.checkExpiredDates();
            assertEquals('Y', tsObj.allTrade.get("T10").getExpired());
        }

        //Empty the HashMap to add / update given testcase from the table
        void removeAllTrade() {
            tsObj.allTrade.clear();
        }

        //Check the testcase for T1	1	CP-1	B1	20/05/2020	<today date>	N
        //Adding the trade will fail so Checking the size of the map to be empty
        @Test
        @Order(11)
        void test1() throws Exception {
            Date maturityDate = sd.parse("20/05/2020");
            TradeModel t17 = new TradeModel("T1", 1, "CP-1", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t17);
            assertEquals(0, tsObj.allTrade.size());
        }

        //Check the testcase for T2	2	CP-2	B1	20/05/2021	<today date>	N
        //Adding the trade will be added in the trade map
        @Test
        @Order(11)
        void test2() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");
            TradeModel t18 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t18);
            assertEquals(1, tsObj.allTrade.size());
        }

        //Check the testcase for T2	1	CP-1	B1	20/05/2021	14/03/2015	N
        //Adding the trade will not be added to the trade list
        @Test
        @Order(11)
        void test3() throws Exception {
            Date maturityDate = sd.parse("20/05/2021");
            TradeModel t18 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
            tsObj.addTrade(t18);
            assertEquals(1, tsObj.allTrade.size());
            maturityDate = sd.parse("20/05/2021");
            Date createdDate = sd.parse("14/03/2015");
            TradeModel t19 = new TradeModel("T2", 1, "CP-2", "B1", maturityDate, createdDate, 'N');

            assertThrows(Exception.class, () -> tsObj.addTrade(t19));
        }

        @Test
        @Order(12)
        void test4() throws Exception {
            Date maturityDate = sd.parse("20/05/2020");
            TradeModel t17 = new TradeModel("T1", 1, "CP-1", "B1", maturityDate, todaysDate, 'N');
            maturityDate = sd.parse("20/05/2021");
            TradeModel t18 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');

            maturityDate = sd.parse("20/05/2020");
            TradeModel t20 = new TradeModel("T3", 3, "CP-3", "B2", maturityDate, todaysDate, 'N');
            tsObj.allTrade.put("T3", t20);
            tsObj.checkExpiredDates();
            assertEquals('Y', tsObj.allTrade.get("T3").getExpired());
        }
    }
