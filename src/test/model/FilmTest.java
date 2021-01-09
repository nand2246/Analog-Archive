package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    private Film f;
    private Camera c;

    @BeforeEach
    public void runBefore() {
        c = new Camera("Program Plus", "35mm", "Pentax");
        f = new Film("UltraMax", 400, "35mm", c, "Kodak");
    }

    @Test
    public void testIsExpiredTrue() {
        f.setDevelopDate(2019, 9, 22);
        f.setExpiry(2001, 6, 30);
        assertTrue(f.isExpired());
    }

    @Test
    public void testGetExpiry() {
        f.setExpiry(2001, 6, 30);
        LocalDate date = LocalDate.of(2001, 6, 30);
        assertEquals(date, f.getExpiry());
    }

    @Test
    public void testGetDevelopDate() {
        f.setDevelopDate(2020, 9, 22);
        LocalDate date = LocalDate.of(2020, 9, 22);
        assertEquals(date, f.getDevelopDate());
    }

    @Test
    public void testIsExpiredFalse() {
        f.setDevelopDate(2019, 9, 22);
        f.setExpiry(2020, 6, 30);
        assertFalse(f.isExpired());
    }

    @Test
    public void testSetName() {
        f.setName("Superia");
        assertEquals("Superia", f.getName());
    }

    @Test
    public void testSetIso() {
        f.setIso(200);
        assertEquals(200, f.getIso());
    }

    @Test
    public void testSetType() {
        f.setType("medium format");
        assertEquals("medium format", f.getType());
    }

    @Test
    public void testSetCameraSameType() {
        c = new Camera("Impulse AF", "35mm", "Polaroid");
        assertTrue(f.setCamera(c));
    }

    @Test
    public void testSetCameraDifferentType() {
        c = new Camera("Impulse AF", "medium format", "Polaroid");
        assertFalse(f.setCamera(c));
    }

    @Test
    public void testSetManufacturer() {
        f.setBrand("Polaroid");
        assertEquals("Polaroid", f.getBrand());
    }

    @Test
    public void testSetDevelopLocation() {
        f.setDevelopLocation("Kerrisdale Camera");
        assertEquals("Kerrisdale Camera", f.getDevelopLocation());
    }

    @Test
    public void testSetImagesPath() {
        f.setDirectory("C:\\Users\\nandp\\Downloads");
        assertEquals("C:\\Users\\nandp\\Downloads", f.getDirectory());
    }

    @Test
    public void testToString() {
        assertEquals("UltraMax, Kodak, 35mm", f.toString());
    }
}