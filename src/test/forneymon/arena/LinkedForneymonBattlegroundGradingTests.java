package forneymon.arena;

import forneymon.arena.*;
import forneymon.species.*;
import static forneymon.arena.ForneymonArena.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import static org.junit.Assert.*;

import org.junit.AfterClass;

public class LinkedForneymonBattlegroundGradingTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Global timeout to prevent infinite loops from
    // crashing the test suite
    // [!] Comment out the next 2 lines if you're using
    // the debugger!
    @Rule
    public Timeout globalTimeout = Timeout.seconds(1);

    // Grade record-keeping
    static int possible = 0, passed = 0;

    // Each time you pass a test, you get a point! Yay!
    // [!] Requires JUnit 4+ to run
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            passed++;
        }
    };

    // Used as the basic empty LinkedForneymonagerie to test; the @Before
    // method is run before every @Test
    LinkedForneymonagerie fm1;
    @Before
    public void init () {
        possible++;
        fm1 = new LinkedForneymonagerie();
    }

    // Used for grading, reports the total number of tests
    // passed over the total possible
    @AfterClass
    public static void gradeReport () {
        System.out.println("============================");
        System.out.println("Tests Complete");
        System.out.println(passed + " / " + possible + " passed!");
        if ((1.0 * passed / possible) >= 0.9) {
            System.out.println("[!] Nice job!"); // Automated acclaim!
        }
        System.out.println("============================");
    }


    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void size_t0() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        assertEquals(1, fm1.size());
    }
    @Test
    public void size_t1() {
        assertEquals(0, fm1.size());
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        assertEquals(2, fm1.size());
    }
    @Test
    public void size_t2() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(2));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        assertEquals(5, fm1.size());
    }
    @Test
    public void size_t3() {
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(2));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals(9, fm1.size());
    }


    @Test
    public void collect_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertTrue(fm1.containsSpecies("Burnymon"));
        assertTrue(!fm1.containsSpecies("Zappymon"));
        assertEquals(2, fm1.size());
        assertEquals("Burnymon", fm1.get(1).getSpecies());
    }
    @Test
    public void collect_t1() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        fm1.collect(d1);
        fm1.collect(d1);
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertEquals(1, fm1.get(0).getLevel());
        fm1.collect(d2);
        assertEquals(2, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }
    @Test
    public void collect_t2() {
        Dampymon d1 = new Dampymon(1);
        Dampymon d2 = new Dampymon(2);
        fm1.collect(d1);
        assertTrue(fm1.containsSpecies("Dampymon"));
        fm1.collect(d2);
        assertEquals(2, fm1.get(0).getLevel());
        fm1.collect(d1);
        assertEquals(2, fm1.get(0).getLevel());
        assertEquals(1, fm1.size());
    }
    @Test
    public void collect_t3() {
        boolean c1 = fm1.collect(new Dampymon(1)),
                c2 = fm1.collect(new Burnymon(2)),
                c3 = fm1.collect(new Zappymon(3)),
                c4 = fm1.collect(new Burnymon(4));
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(2, fm1.getSpeciesIndex("Zappymon"));
        assertEquals(4, fm1.get(1).getLevel());
        assertTrue(c1);
        assertTrue(c2);
        assertTrue(c3);
        assertFalse(c4);
    }
    @Test
    public void collect_t4() {
        Leafymon leafy = new Leafymon(3);
        assertEquals(0, fm1.size());
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(leafy);
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.collect(new Dampymon(2));
        fm1.collect(new Burnymon(2));
        fm1.collect(leafy);
        assertEquals(2, fm1.get(0).getLevel());
        assertEquals(2, fm1.get(1).getLevel());
        assertEquals(3, fm1.get(2).getLevel());
    }


    @Test
    public void releaseSpecies_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(2, fm1.size());
        fm1.releaseSpecies("Dampymon");
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsSpecies("Burnymon"));
        assertTrue(!fm1.containsSpecies("Dampymon"));
    }
    @Test
    public void releaseSpecies_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        boolean rt1 = fm1.releaseSpecies("Burnymon");
        assertEquals(2, fm1.size());
        assertEquals("Dampymon", fm1.get(0).getSpecies());
        assertEquals("Leafymon", fm1.get(1).getSpecies());
        assertTrue(!fm1.containsSpecies("Burnymon"));
        boolean rt2 = fm1.releaseSpecies("Burnymon");
        assertTrue(rt1);
        assertFalse(rt2);
    }
    @Test
    public void releaseSpecies_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.releaseSpecies("Dampymon");
        assertEquals(2, fm1.size());
        assertEquals("Burnymon", fm1.get(0).getSpecies());
        assertEquals("Leafymon", fm1.get(1).getSpecies());
        assertEquals(-1, fm1.getSpeciesIndex("Dampymon"));
        assertTrue(!fm1.containsSpecies("Dampymon"));
        assertThrows(IllegalArgumentException.class, () -> fm1.get(2));
    }
    @Test
    public void releaseSpecies_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.releaseSpecies("Dampymon");
        fm1.releaseSpecies("Zappymon");
        assertEquals(7, fm1.size());
        assertEquals("Burnymon", fm1.get(0).getSpecies());
        assertEquals("Leafymon", fm1.get(1).getSpecies());
        assertEquals("TestForneymon0", fm1.get(2).getSpecies());
        assertEquals("TestForneymon1", fm1.get(3).getSpecies());
    }
    @Test
    public void releaseSpecies_t4() {
        fm1.releaseSpecies("Dampymon");
        assertTrue(!fm1.containsSpecies("Dampymon"));
    }


    @Test
    public void get_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(d1, fm1.get(0));
        assertEquals(b1, fm1.get(1));
    }
    @Test
    public void get_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertThrows(IllegalArgumentException.class, () -> fm1.get(-1));
    }
    @Test
    public void get_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertThrows(IllegalArgumentException.class, () -> fm1.get(2));
    }
    @Test
    public void get_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals("TestForneymon4", fm1.get(8).getSpecies());
    }


    @Test
    public void getMVP_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        Forneymon result = fm1.getMVP();
        assertEquals("Leafymon", result.getSpecies());
        assertEquals(3, result.getLevel());
    }
    @Test
    public void getMVP_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        fm1.get(2).takeDamage(10, DamageType.BASIC);
        Forneymon result = fm1.getMVP();
        assertEquals("Burnymon", result.getSpecies());
        assertEquals(3, result.getLevel());
    }
    @Test
    public void getMVP_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        fm1.get(2).takeDamage(5, DamageType.BASIC);
        Forneymon result = fm1.getMVP();
        assertEquals("Burnymon", result.getSpecies());
        assertEquals(3, result.getLevel());
    }
    @Test
    public void getMVP_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(4));
        fm1.collect(new Burnymon(3));
        fm1.get(2).takeDamage(5, DamageType.BASIC);
        Forneymon result = fm1.getMVP();
        assertEquals("Zappymon", result.getSpecies());
        assertEquals(4, result.getLevel());
    }


    @Test
    public void testRemove_t0() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(2, fm1.size());
        fm1.remove(0);
        assertEquals(1, fm1.size());
        assertEquals(b1, fm1.get(0));
    }
    @Test
    public void testRemove_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        assertEquals(2, fm1.size());
        assertEquals(d1, fm1.remove(0));
        assertEquals(1, fm1.size());
        assertEquals(b1, fm1.remove(0));
        assertEquals(0, fm1.size());

        assertThrows(IllegalArgumentException.class, () -> fm1.remove(0));
    }
    @Test
    public void testRemove_t2() {
        Burnymon b1 = new Burnymon(2);
        TestForneymon0 t1 = new TestForneymon0(1);
        TestForneymon4 t5 = new TestForneymon4(1);
        fm1.collect(new Dampymon(1));
        fm1.collect(b1);
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(t1);
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(t5);
        assertEquals(b1, fm1.remove(1));
        assertEquals(t1, fm1.remove(3));
        assertEquals(t5, fm1.remove(6));
    }


    @Test
    public void getSpeciesIndexContainsSpecies_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(-1, fm1.getSpeciesIndex("Leafymon"));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertFalse(fm1.containsSpecies("Zappymon"));
    }
    @Test
    public void getSpeciesIndexContainsSpecies_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Dampymon(1));
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(-1, fm1.getSpeciesIndex("Leafymon"));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertFalse(fm1.containsSpecies("Zappymon"));
    }
    @Test
    public void getSpeciesIndexContainsSpecies_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(2, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(3, fm1.getSpeciesIndex("Zappymon"));
        assertEquals(4, fm1.getSpeciesIndex("TestForneymon0"));
        assertEquals(8, fm1.getSpeciesIndex("TestForneymon4"));
        assertEquals(-1, fm1.getSpeciesIndex("TestForneymon5"));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertTrue(fm1.containsSpecies("TestForneymon2"));
        assertFalse(fm1.containsSpecies("TestForneymon5"));
    }


    @Test
    public void rearrange_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.rearrange("Leafymon", 0);
        assertEquals(1, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(2, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
    }
    @Test
    public void rearrange_t1() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.rearrange("Leafymon", 0);
        fm1.collect(new Burnymon(1));
        fm1.collect(new Dampymon(1));
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        assertEquals(1, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(2, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(e1, fm1.get(0));
        assertEquals(d1, fm1.get(1));
        assertEquals(b1, fm1.get(2));
    }
    @Test
    public void rearrange_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        fm1.rearrange("Dampymon", 0);
        fm1.rearrange("Zappymon", 3);
        assertEquals(0, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(2, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(3, fm1.getSpeciesIndex("Zappymon"));
    }
    @Test
    public void rearrange_t3() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.rearrange("Burnymon", 1);
        fm1.rearrange("Leafymon", 1);
        assertEquals(d1, fm1.get(0));
        assertEquals(e1, fm1.get(1));
        assertEquals(b1, fm1.get(2));
        assertEquals(z1, fm1.get(3));
    }
    @Test
    public void rearrange_t4() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.rearrange("Burnymon", 3);
        assertEquals(d1, fm1.get(0));
        assertEquals(e1, fm1.get(1));
        assertEquals(z1, fm1.get(2));
        assertEquals(b1, fm1.get(3));
    }
    @Test
    public void rearrange_t5() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.rearrange("Dampymon", 8);
        fm1.rearrange("TestForneymon4", 0);
        assertEquals(8, fm1.getSpeciesIndex("Dampymon"));
        assertEquals(1, fm1.getSpeciesIndex("Burnymon"));
        assertEquals(2, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(3, fm1.getSpeciesIndex("Zappymon"));
        assertEquals(4, fm1.getSpeciesIndex("TestForneymon0"));
        assertEquals(0, fm1.getSpeciesIndex("TestForneymon4"));
        assertEquals(-1, fm1.getSpeciesIndex("TestForneymon5"));
    }
    @Test
    public void rearrange_t6() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        assertThrows(IllegalArgumentException.class, () -> fm1.rearrange("Leafymon", 4));
    }
    @Test
    public void rearrange_t7() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.remove(2);
        fm1.rearrange("Dampymon", 2);
        assertEquals(b1, fm1.get(0));
        assertEquals(z1, fm1.get(1));
        assertEquals(d1, fm1.get(2));
        assertEquals(-1, fm1.getSpeciesIndex("Leafymon"));
    }


    @Test
    public void clone_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie dolly = fm1.clone();
        assertEquals(3, dolly.size());

        fm1.get(0).takeDamage(5, DamageType.BASIC);
        assertEquals(Dampymon.START_HEALTH - 5, fm1.get(0).getHealth());
        assertEquals(Dampymon.START_HEALTH, dolly.get(0).getHealth());

        fm1.rearrange("Leafymon", 0);
        assertEquals(0, fm1.getSpeciesIndex("Leafymon"));
        assertEquals(2, dolly.getSpeciesIndex("Leafymon"));
    }
    @Test
    public void clone_t1() {
        LinkedForneymonagerie dolly = fm1.clone();
        assertEquals(0, dolly.size());
        dolly.collect(new Dampymon(1));
        assertEquals(0, fm1.size());
        assertEquals(0, dolly.getSpeciesIndex("Dampymon"));
        assertEquals(-1, fm1.getSpeciesIndex("Dampymon"));
    }
    @Test
    public void clone_t2() {
        Dampymon d1 = new Dampymon(1);
        fm1.collect(d1);
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        LinkedForneymonagerie dolly = fm1.clone();
        dolly.collect(d1);
        fm1.collect(d1);
        assertEquals(9, dolly.size());
        assertEquals(1, fm1.get(0).getLevel());
        assertEquals(1, dolly.get(0).getLevel());
    }


    @Test
    public void trade_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Leafymon(1));
        fm1.trade(fm2);

        assertEquals(2, fm2.size());
        assertEquals(1, fm1.size());
        assertTrue(fm1.containsSpecies("Leafymon"));
        assertTrue(!fm1.containsSpecies("Dampymon"));
        assertTrue(fm2.containsSpecies("Dampymon"));
        assertTrue(!fm2.containsSpecies("Leafymon"));
    }
    @Test
    public void trade_t1() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.trade(fm2);
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        assertTrue(fm1.containsSpecies("Dampymon"));
        assertFalse(fm2.containsSpecies("Dampymon"));
        fm2.trade(fm1);
        assertTrue(fm2.containsSpecies("Dampymon"));
        assertFalse(fm1.containsSpecies("Dampymon"));
    }
    @Test
    public void trade_t2() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        LinkedForneymonagerie fm3 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm1.trade(fm2);
        fm2.trade(fm3);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
        assertEquals(9, fm3.size());
    }


    @Test
    public void equals_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));

        assertEquals(fm1, fm2);
        fm2.rearrange("Burnymon", 0);
        assertNotEquals(fm1, fm2);
    }
    @Test
    public void equals_t1() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        assertEquals(fm1, fm2);
    }
    @Test
    public void equals_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = fm1.clone();
        assertEquals(fm1, fm2);
        assertEquals(fm1, fm1);
    }
    @Test
    public void equals_t3() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(2));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Burnymon(1));
        fm2.collect(new Leafymon(3));
        fm2.collect(new Zappymon(2));
        fm2.collect(new TestForneymon0(1));
        fm2.collect(new TestForneymon1(1));
        fm2.collect(new TestForneymon2(1));
        fm2.collect(new TestForneymon3(1));
        fm2.collect(new TestForneymon4(1));
        assertNotEquals(fm1, fm2);
        fm2.collect(new Burnymon(2));
        assertEquals(fm1, fm2);
    }
    @Test
    public void equals_t4() {
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(3));
        fm1.collect(new Leafymon(3));
        fm1.collect(new Zappymon(2));
        fm1.collect(new TestForneymon0(1));
        fm1.collect(new TestForneymon1(1));
        fm1.collect(new TestForneymon2(1));
        fm1.collect(new TestForneymon3(1));
        fm1.collect(new TestForneymon4(1));
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Leafymon(3));
        fm2.collect(new Zappymon(2));
        fm2.collect(new TestForneymon0(1));
        fm2.collect(new TestForneymon1(1));
        fm2.collect(new TestForneymon2(1));
        fm2.collect(new TestForneymon3(1));
        fm2.collect(new TestForneymon4(1));
        fm1.remove(0);
        fm2.remove(1);
        assertEquals(fm1, fm2);
    }


    // =================================================
    // Integration Tests
    // =================================================

    @Test
    public void fight_t0() {
        fm1.collect(new Dampymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));

        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void fight_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(1));
        fm2.collect(new Dampymon(1));

        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(0, fm2.size());
    }
    @Test
    public void fight_t2() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(1));
        fm2.collect(new Zappymon(1));

        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void fight_t3() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(5));
        fm2.collect(new Dampymon(5));

        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(1, fm2.size());
    }
    @Test
    public void fight_t4() {
        fm1.collect(new Burnymon(5));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Dampymon(1));

        fight(fm1, fm2, false);
        assertEquals(1, fm1.size());
        assertEquals(0, fm2.size());
        assertEquals(3, fm1.get(0).getHealth());
        assertFalse(fm2.containsSpecies("Dampymon"));
    }
    @Test
    public void fight_t5() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(3));
        fm2.collect(new Zappymon(3));
        fm2.collect(new Leafymon(3));

        fight(fm1, fm2, false);
        assertEquals(0, fm1.size());
        assertEquals(2, fm2.size());
    }
    @Test
    public void fight_t6() {
        fm1.collect(new Dampymon(3));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Burnymon(3));
        fm2.collect(new Dampymon(3));
        fm2.collect(new Zappymon(3));
        fm2.collect(new Leafymon(3));

        fight(fm1, fm2, false);
        assertEquals(8, fm2.get(0).getHealth());
        assertEquals("Zappymon", fm2.get(0).getSpecies());
        assertEquals(2, fm2.get(1).getHealth());
        assertEquals("Leafymon", fm2.get(1).getSpecies());
    }

    @Test
    public void testIterator_t0() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        assertTrue(it.isValid());
        assertTrue(it.atStart());
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        it.next();
        assertEquals("Burnymon", it.getCurrent().getSpecies());
        it.next();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        assertTrue(it.atEnd());
        it.next();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Burnymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        fm1.remove(0);
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t1() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        it.next();
        assertEquals("Burnymon", it.getCurrent().getSpecies());
        it.removeCurrent();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        assertEquals(2, fm1.size());
        assertFalse(fm1.containsSpecies("Burnymon"));
    }
    @Test
    public void testIterator_t2() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        it.removeCurrent();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Burnymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        assertEquals(2, fm1.size());
        it.removeCurrent();
        it.removeCurrent();
        assertEquals(0, fm1.size());
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t3() {
        Dampymon d1 = new Dampymon(1);
        fm1.collect(d1);
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        fm1.collect(d1);
        assertTrue(it.isValid());
        fm1.collect(new Dampymon(2));
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t4() {
        Forneymon[] results = {
            new Dampymon(1),
            new Burnymon(1),
            new Leafymon(1),
            new Zappymon(1),
            new TestForneymon0(1),
            new TestForneymon1(1),
            new TestForneymon2(1),
            new TestForneymon3(1),
            new TestForneymon4(1)
        };
        for (Forneymon f : results) {
            fm1.collect(f);
        }
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        for (int i = 0; i < fm1.size(); i++, it.next()) {
            assertEquals(results[i], it.getCurrent());
        }
        it.prev();
        for (int i = fm1.size()-1; i > 0; i--, it.prev()) {
            assertEquals(results[i], it.getCurrent());
        }
    }
    @Test
    public void testIterator_t5() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        fm1.releaseSpecies("Zappymon");
        assertTrue(it.isValid());
        fm1.releaseSpecies("Burnymon");
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t6() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        fm1.remove(2);
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t7() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        fm1.rearrange("Dampymon", 0);
        assertTrue(it.isValid());
        fm1.rearrange("Leafymon", 0);
        assertFalse(it.isValid());
    }
    @Test
    public void testIterator_t8() {
        Dampymon d1 = new Dampymon(1);
        Burnymon b1 = new Burnymon(1);
        Leafymon e1 = new Leafymon(1);
        Zappymon z1 = new Zappymon(1);
        fm1.collect(d1);
        fm1.collect(b1);
        fm1.collect(e1);
        fm1.collect(z1);
        fm1.rearrange("Zappymon", 0);
        fm1.rearrange("Burnymon", 3);
        Forneymon[] results = {z1, d1, e1, b1};
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        assertTrue(it.isValid());
        for (int i = 0; i < fm1.size(); i++, it.next()) {
            assertEquals(results[i], it.getCurrent());
        }
        it.prev();
        for (int i = fm1.size()-1; i > 0; i--, it.prev()) {
            assertEquals(results[i], it.getCurrent());
        }
    }
    @Test
    public void testIterator_t9() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator();
        assertTrue(it1.isValid());
        fm1.trade(fm2);
        assertFalse(it1.isValid());
    }
    @Test
    public void testIterator_t10() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = new LinkedForneymonagerie();
        fm2.collect(new Zappymon(1));
        fm2.collect(new Burnymon(1));
        fm2.collect(new Leafymon(1));
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm2.getIterator();
        assertTrue(it1.isValid());
        assertTrue(it2.isValid());
        fm1.trade(fm2);
        assertFalse(it1.isValid());
        assertFalse(it2.isValid());
    }
    @Test
    public void testIterator_t11() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = fm1.clone();
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm2.getIterator();
        assertTrue(it1.isValid());
        assertTrue(it2.isValid());
        fm1.collect(new Zappymon(1));
        assertFalse(it1.isValid());
        assertTrue(it2.isValid());
    }
    @Test
    public void testIterator_t12() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        LinkedForneymonagerie fm2 = fm1.clone();
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm2.getIterator();
        it1.next();
        it2.next();
        it1.removeCurrent();
        assertEquals("Burnymon", it2.getCurrent().getSpecies());
        assertEquals(2, fm1.size());
    }
    @Test
    public void testIterator_t13() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        fm1.remove(1);
        fm1.remove(2);
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        it.next();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        it.next();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
        it.prev();
        assertEquals("Dampymon", it.getCurrent().getSpecies());
    }
    @Test
    public void testIterator_t14() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie.Iterator it = fm1.getIterator();
        it.removeCurrent();
        assertEquals("Zappymon", it.getCurrent().getSpecies());
        it.removeCurrent();
        assertEquals("Leafymon", it.getCurrent().getSpecies());
    }
    @Test
    public void testIterator_t15() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie fm2 = fm1.clone();
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm2.getIterator();
        while (!fm1.empty()) {
            assertEquals(it1.removeCurrent(), it2.removeCurrent());
        }
    }
    @Test
    public void testIterator_t16() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm1.getIterator();
        for (int i = 0; i < fm1.size(); i++) {
            assertEquals(it1.getCurrent(), it2.getCurrent());
        }
    }
    @Test
    public void testIterator_t17() {
        fm1.collect(new Dampymon(1));
        fm1.collect(new Burnymon(1));
        fm1.collect(new Leafymon(1));
        fm1.collect(new Zappymon(1));
        LinkedForneymonagerie.Iterator it1 = fm1.getIterator(),
                                       it2 = fm1.getIterator();
        it1.removeCurrent();
        assertTrue(it1.isValid());
        assertFalse(it2.isValid());
    }

}
