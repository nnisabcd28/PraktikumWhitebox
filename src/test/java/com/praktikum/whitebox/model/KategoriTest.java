package com.praktikum.whitebox.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Lengkap Class Kategori - Full Coverage 100%")
public class KategoriTest {

    private Kategori kategori;

    @BeforeEach
    void setUp() {
        kategori = new Kategori("K01", "Elektronik", "Barang-barang elektronik");
    }

    // ======================================================
    // ================ Konstruktor =========================
    // ======================================================

    @Test
    @DisplayName("Konstruktor dengan parameter harus menyimpan nilai dengan benar")
    void testKonstruktorDenganParameter() {
        assertEquals("K01", kategori.getKode());
        assertEquals("Elektronik", kategori.getNama());
        assertEquals("Barang-barang elektronik", kategori.getDeskripsi());
        assertTrue(kategori.isAktif());
    }

    @Test
    @DisplayName("Konstruktor tanpa parameter dapat digunakan dan setter berfungsi")
    void testKonstruktorTanpaParameter() {
        Kategori k = new Kategori();
        k.setKode("K02");
        k.setNama("Fashion");
        k.setDeskripsi("Barang pakaian dan aksesoris");
        k.setAktif(false);

        assertEquals("K02", k.getKode());
        assertEquals("Fashion", k.getNama());
        assertEquals("Barang pakaian dan aksesoris", k.getDeskripsi());
        assertFalse(k.isAktif());
    }

    // ======================================================
    // ================ Setter & Getter =====================
    // ======================================================

    @Test
    @DisplayName("Setter dan getter bekerja dengan benar")
    void testSetterGetter() {
        kategori.setKode("K99");
        kategori.setNama("Otomotif");
        kategori.setDeskripsi("Suku cadang dan aksesoris mobil");
        kategori.setAktif(false);

        assertEquals("K99", kategori.getKode());
        assertEquals("Otomotif", kategori.getNama());
        assertEquals("Suku cadang dan aksesoris mobil", kategori.getDeskripsi());
        assertFalse(kategori.isAktif());
    }

    // ======================================================
    // ================ equals() dan hashCode() =============
    // ======================================================

    @Test
    @DisplayName("equals() true jika kode sama")
    void testEqualsTrue() {
        Kategori k2 = new Kategori("K01", "Elektronik Rumah", "Deskripsi lain");
        assertEquals(kategori, k2);
        assertEquals(kategori.hashCode(), k2.hashCode());
    }

    @Test
    @DisplayName("equals() false jika kode berbeda")
    void testEqualsFalseKodeBerbeda() {
        Kategori k2 = new Kategori("K02", "Fashion", "Deskripsi");
        assertNotEquals(kategori, k2);
    }

    @Test
    @DisplayName("equals() false jika dibandingkan dengan null")
    void testEqualsNull() {
        assertNotEquals(kategori, null);
    }

    @Test
    @DisplayName("equals() false jika dibandingkan dengan tipe berbeda")
    void testEqualsDifferentType() {
        assertNotEquals(kategori, "String");
    }

    @Test
    @DisplayName("equals() true jika dibandingkan dengan objek yang sama (self)")
    void testEqualsSelf() {
        assertEquals(kategori, kategori);
    }

    // ======================================================
    // ================ hashCode() ==========================
    // ======================================================

    @Test
    @DisplayName("hashCode() menghasilkan nilai yang konsisten untuk kode yang sama")
    void testHashCodeConsistent() {
        int h1 = kategori.hashCode();
        int h2 = kategori.hashCode();
        assertEquals(h1, h2);
    }

    // ======================================================
    // ================ toString() ==========================
    // ======================================================

    @Test
    @DisplayName("toString() mengandung informasi penting kategori")
    void testToString() {
        String hasil = kategori.toString();
        assertTrue(hasil.contains("K01"));
        assertTrue(hasil.contains("Elektronik"));
        assertTrue(hasil.contains("Barang-barang elektronik"));
    }

    // ======================================================
    // ================ Status Aktif ========================
    // ======================================================

    @Test
    @DisplayName("isAktif() harus benar saat aktif dan false saat di-nonaktifkan")
    void testIsAktif() {
        assertTrue(kategori.isAktif());
        kategori.setAktif(false);
        assertFalse(kategori.isAktif());
    }
}
