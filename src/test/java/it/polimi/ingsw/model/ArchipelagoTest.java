package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchipelagoTest {

    private List<Archipelago> archipelagos;

    @BeforeEach
    void setUp() {
        archipelagos = new ArrayList<>();
        archipelagos.add(new Archipelago(new Student(Color.GREEN), false));
        archipelagos.add(new Archipelago(new Student(Color.RED), true));
        archipelagos.add(new Archipelago(null, false));
        //Archipelago0 = {{green}}
        //Archipelago1 = {{red}}
        //Archipelago2 = {{ }}
    }

    @AfterEach
    void tearDown() {
        archipelagos = null;
    }

    @Test
    void testIsMNPresent(){
        assertFalse(archipelagos.get(0).isMNPresent());
        assertTrue(archipelagos.get(1).isMNPresent());
    }

    @Test
    void testSetMotherNature(){
        archipelagos.get(0).setMotherNature(true);
        archipelagos.get(1).setMotherNature(false);
        assertTrue(archipelagos.get(0).isMNPresent());
        assertFalse(archipelagos.get(1).isMNPresent());
    }

    @Test
    void testSetNoEntryTile(){
        archipelagos.get(0).setNoEntryTile(true);
        assertTrue(archipelagos.get(0).isNoEntryTilePresent());
    }

    @Test
    void testIsNoEntryTilePresent(){
        assertFalse(archipelagos.get(0).isNoEntryTilePresent());
    }

    @Test
    void testSetTowerColor(){
        archipelagos.get(0).setTowerColor(TowerColor.BLACK);
        assertEquals(TowerColor.BLACK, archipelagos.get(0).getTowerColor());
    }

    @Test
    void testGetTowerColor(){
        for (Archipelago a : archipelagos){
            assertNull(a.getTowerColor());
        }
    }

    @Test
    void testAddIsland() {
        for (Archipelago a : archipelagos){
            assertEquals(1, a.getIslands().size());
            a.addIsland(new Island(new Student(Color.PINK)));
            assertEquals(2, a.getIslands().size());
        }
        //Archipelago0 = {{green}, {pink}}
        //Archipelago1 = {{red}, {pink}}
        //Archipelago2 = {{ }, {pink}}


        List<Island> islands2 = archipelagos.get(2).getIslands();
        for (Island i : islands2){
            archipelagos.get(0).addIsland(i);
        }
        assertEquals(4, archipelagos.get(0).getIslands().size());
        //Archipelago0 = {{green}, {pink}, {}, {pink}}
    }

    @Test
    void testGetTotalStudents() {
        Color[] colors = Color.values();

        for (Archipelago a : archipelagos){
            for(Color c : Color.values()){
                if((c == Color.GREEN && archipelagos.indexOf(a) == 0) || (c == Color.RED && archipelagos.indexOf(a) == 1)){
                    assertEquals(1, a.getTotalStudents(c));
                } else{
                    assertEquals(0, a.getTotalStudents(c));
                }
            }
        }

        archipelagos.get(0).getIslands().get(0).addStudent(new Student(Color.BLUE));
        archipelagos.get(0).getIslands().get(0).addStudent(new Student(Color.YELLOW));
        archipelagos.get(0).getIslands().get(0).addStudent(new Student(Color.GREEN));
        //Archipelago0 = {{green, blue, yellow, green}}

        archipelagos.get(1).addIsland(new Island(new Student(Color.RED)));
        archipelagos.get(1).getIslands().get(1).addStudent(new Student(Color.YELLOW));
        archipelagos.get(1).getIslands().get(0).addStudent(new Student(Color.BLUE));
        //Archipelago1 = {{red, blue}, {red, yellow}}

        archipelagos.get(2).addIsland(new Island(new Student(Color.YELLOW)));
        archipelagos.get(2).getIslands().get(1).addStudent(new Student(Color.RED));
        archipelagos.get(2).getIslands().get(0).addStudent(new Student(Color.PINK));
        //Archipelago2 = {{pink}, {yellow, red}}

        for(Color c : Color.values()){
            int tot0 = archipelagos.get(0).getTotalStudents(c);
            int tot1 = archipelagos.get(1).getTotalStudents(c);
            int tot2 = archipelagos.get(2).getTotalStudents(c);
            switch (c) {
                case GREEN:
                    assertEquals(2, tot0);
                    assertEquals(0, tot1);
                    assertEquals(0, tot2);
                    break;
                case RED:
                    assertEquals(0, tot0);
                    assertEquals(2, tot1);
                    assertEquals(1, tot2);
                    break;
                case BLUE:
                    assertEquals(1, tot0);
                    assertEquals(1, tot1);
                    assertEquals(0, tot2);
                    break;
                case YELLOW:
                    assertEquals(1, tot0);
                    assertEquals(1, tot1);
                    assertEquals(1, tot2);
                    break;
                case PINK:
                    assertEquals(0, tot0);
                    assertEquals(0, tot1);
                    assertEquals(1, tot2);
                    break;
                default: break;
            }
        }

        Archipelago.setForbiddenColor(Color.RED);
        for (Archipelago a : archipelagos){
            assertEquals(0, a.getTotalStudents(Color.RED));
        }

        Archipelago.resetForbiddenColor();
        assertEquals(0, archipelagos.get(0).getTotalStudents(Color.RED));
        assertEquals(2, archipelagos.get(1).getTotalStudents(Color.RED));
        assertEquals(1, archipelagos.get(2).getTotalStudents(Color.RED));
    }
}