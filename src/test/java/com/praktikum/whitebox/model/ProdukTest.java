package com.praktikum.whitebox.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Lengkap Class Produk - White Box Testing 100% Coverage")
public class ProdukTest {
    private Produk produk;

    @BeforeEach
    void setUp() {
        produk = new Produk("PROD001", "Laptop Gaming", "Elektronik", 15000000, 10, 5);
    }

    // ======================================================
    // ================ Konstruktor =========================
    // ======================================================

    @Test
    @DisplayName("Konstruktor tanpa argumen - inisialisasi default berhasil")
    void testKonstruktorTanpaArgumen() {
        Produk p = new Produk();
        assertNotNull(p);

        // Uji setter dan getter
        p.setKode("K01");
        p.setNama("Monitor");
        p.setKategori("Elektronik");
        p.setHarga(2000000);
        p.setStok(5);
        p.setStokMinimum(2);
        p.setAktif(true);

        assertEquals("K01", p.getKode());
        assertEquals("Monitor", p.getNama());
        assertEquals("Elektronik", p.getKategori());
        assertEquals(2000000, p.getHarga());
        assertEquals(5, p.getStok());
        assertEquals(2, p.getStokMinimum());
        assertTrue(p.isAktif());
    }

    // ======================================================
    // ================ Setter & Getter =====================
    // ======================================================

    @Test
    @DisplayName("Setter dan getter berfungsi dengan benar")
    void testSetterGetter() {
        produk.setKode("NEW01");
        produk.setNama("Keyboard");
        produk.setKategori("Aksesoris");
        produk.setHarga(500000);
        produk.setStok(12);
        produk.setStokMinimum(4);
        produk.setAktif(false);

        assertEquals("NEW01", produk.getKode());
        assertEquals("Keyboard", produk.getNama());
        assertEquals("Aksesoris", produk.getKategori());
        assertEquals(500000, produk.getHarga());
        assertEquals(12, produk.getStok());
        assertEquals(4, produk.getStokMinimum());
        assertFalse(produk.isAktif());
    }

    // ======================================================
    // ================ Status Stok =========================
    // ======================================================

    @Test
    @DisplayName("Status stok aman")
    void testStokAman() {
        produk.setStok(10);
        produk.setStokMinimum(5);
        assertTrue(produk.isStokAman());
        assertFalse(produk.isStokMenipis());
        assertFalse(produk.isStokHabis());
    }

    @Test
    @DisplayName("Status stok menipis")
    void testStokMenipis() {
        produk.setStok(5);
        produk.setStokMinimum(5);
        assertFalse(produk.isStokAman());
        assertTrue(produk.isStokMenipis());
        assertFalse(produk.isStokHabis());
    }

    @Test
    @DisplayName("Status stok habis")
    void testStokHabis() {
        produk.setStok(0);
        produk.setStokMinimum(5);
        assertTrue(produk.isStokHabis());
        assertFalse(produk.isStokMenipis());
        assertFalse(produk.isStokAman());
    }

    // ======================================================
    // ================ Kurangi & Tambah Stok ===============
    // ======================================================

    @ParameterizedTest
    @DisplayName("Kurangi stok dengan berbagai nilai valid")
    @CsvSource({
            "5, 5",
            "3, 7",
            "10, 0"
    })
    void testKurangiStokValid(int jumlah, int expectedSisa) {
        produk.kurangiStok(jumlah);
        assertEquals(expectedSisa, produk.getStok());
    }

    @Test
    @DisplayName("Kurangi stok - jumlah negatif (exception)")
    void testKurangiStokNegatif() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> produk.kurangiStok(-5));
        assertEquals("Jumlah harus positif", e.getMessage());
    }

    @Test
    @DisplayName("Kurangi stok - stok tidak cukup (exception)")
    void testKurangiStokTidakCukup() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> produk.kurangiStok(20));
        assertEquals("Stok tidak mencukupi", e.getMessage());
    }

    @Test
    @DisplayName("Tambah stok berhasil")
    void testTambahStokValid() {
        produk.tambahStok(5);
        assertEquals(15, produk.getStok());
    }

    @Test
    @DisplayName("Tambah stok gagal - jumlah negatif")
    void testTambahStokNegatif() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> produk.tambahStok(-5));
        assertEquals("Jumlah harus positif", e.getMessage());
    }

    // ======================================================
    // ================ Hitung Total Harga ==================
    // ======================================================

    @ParameterizedTest
    @DisplayName("Hitung total harga valid")
    @CsvSource({
            "1,15000000",
            "2,30000000",
            "5,75000000"
    })
    void testHitungTotalHargaValid(int jumlah, double expectedTotal) {
        assertEquals(expectedTotal, produk.hitungTotalHarga(jumlah), 0.001);
    }

    @Test
    @DisplayName("Hitung total harga gagal - jumlah negatif")
    void testHitungTotalHargaNegatif() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> produk.hitungTotalHarga(-2));
        assertEquals("Jumlah harus positif", e.getMessage());
    }

    // ======================================================
    // ================ equals() dan hashCode() =============
    // ======================================================

    @Test
    @DisplayName("equals() true untuk kode sama")
    void testEqualsTrue() {
        Produk p2 = new Produk("PROD001", "Laptop Baru", "Elektronik", 2000000, 5, 2);
        assertEquals(produk, p2);
        assertEquals(produk.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("equals() false - kode berbeda")
    void testEqualsFalseKodeBerbeda() {
        Produk p2 = new Produk("DIFF001", "Mouse", "Elektronik", 100000, 5, 1);
        assertNotEquals(produk, p2);
    }

    @Test
    @DisplayName("equals() false - dibandingkan dengan null")
    void testEqualsNull() {
        assertNotEquals(produk, null);
    }

    @Test
    @DisplayName("equals() false - dibandingkan dengan tipe lain")
    void testEqualsDifferentType() {
        assertNotEquals(produk, "String");
    }

    @Test
    @DisplayName("equals() true - dibandingkan dengan objek yang sama (self compare)")
    void testEqualsSelf() {
        assertEquals(produk, produk);
    }

    // ======================================================
    // ================ toString() ==========================
    // ======================================================

    @Test
    @DisplayName("toString() mengandung kode, nama, dan kategori produk")
    void testToString() {
        String s = produk.toString();
        assertTrue(s.contains("PROD001"));
        assertTrue(s.contains("Laptop"));
        assertTrue(s.contains("Elektronik"));
    }
}
