package com.praktikum.whitebox.util;

import com.praktikum.whitebox.model.Kategori;
import com.praktikum.whitebox.model.Produk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Lengkap dan Final untuk ValidationUtils (100% Coverage)")
public class ValidationUtilsTest {

    // ========================
    // isValidKodeProduk()
    // ========================

    @Test
    @DisplayName("Kode produk null atau kosong harus false")
    void testKodeProdukNullAtauKosong() {
        assertFalse(ValidationUtils.isValidKodeProduk(null));
        assertFalse(ValidationUtils.isValidKodeProduk(""));
        assertFalse(ValidationUtils.isValidKodeProduk("  "));
    }

    @Test
    @DisplayName("Kode produk tidak sesuai pola regex harus false")
    void testKodeProdukTidakSesuaiRegex() {
        assertFalse(ValidationUtils.isValidKodeProduk("!!@#"));
        assertFalse(ValidationUtils.isValidKodeProduk("ABC123456789")); // lebih dari 10 karakter
    }

    @Test
    @DisplayName("Kode produk valid harus true")
    void testKodeProdukValid() {
        assertTrue(ValidationUtils.isValidKodeProduk("A12"));
        assertTrue(ValidationUtils.isValidKodeProduk("ABC123"));
    }

    // ========================
    // isValidNama()
    // ========================

    @Test
    @DisplayName("Nama null, kosong, atau terlalu panjang harus false")
    void testNamaInvalid() {
        assertFalse(ValidationUtils.isValidNama(null));
        assertFalse(ValidationUtils.isValidNama(""));
        assertFalse(ValidationUtils.isValidNama("   "));
        String namaPanjang = "A".repeat(101);
        assertFalse(ValidationUtils.isValidNama(namaPanjang));
    }

    @Test
    @DisplayName("Nama valid antara 3-100 karakter harus true")
    void testNamaValid() {
        assertTrue(ValidationUtils.isValidNama("Laptop"));
        assertTrue(ValidationUtils.isValidNama("Laptop Gaming 2025"));
    }

    // ========================
    // isValidHarga()
    // ========================

    @Test
    @DisplayName("Harga nol atau negatif harus false")
    void testHargaInvalid() {
        assertFalse(ValidationUtils.isValidHarga(0));
        assertFalse(ValidationUtils.isValidHarga(-1));
    }

    @Test
    @DisplayName("Harga positif harus true")
    void testHargaValid() {
        assertTrue(ValidationUtils.isValidHarga(15000));
    }

    // ========================
    // isValidStok() dan isValidStokMinimum()
    // ========================

    @Test
    @DisplayName("Stok dan stok minimum valid atau tidak valid")
    void testValidasiStokDanStokMinimum() {
        assertTrue(ValidationUtils.isValidStok(0));
        assertTrue(ValidationUtils.isValidStok(5));
        assertFalse(ValidationUtils.isValidStok(-1));

        assertTrue(ValidationUtils.isValidStokMinimum(0));
        assertTrue(ValidationUtils.isValidStokMinimum(10));
        assertFalse(ValidationUtils.isValidStokMinimum(-5));
    }

    // ========================
    // isValidPersentase()
    // ========================

    @Test
    @DisplayName("Persentase di luar 0-100 harus false")
    void testPersentaseInvalid() {
        assertFalse(ValidationUtils.isValidPersentase(-1));
        assertFalse(ValidationUtils.isValidPersentase(101));
    }

    @Test
    @DisplayName("Persentase antara 0-100 harus true")
    void testPersentaseValid() {
        assertTrue(ValidationUtils.isValidPersentase(0));
        assertTrue(ValidationUtils.isValidPersentase(50));
        assertTrue(ValidationUtils.isValidPersentase(100));
    }

    // ========================
    // isValidKuantitas()
    // ========================

    @Test
    @DisplayName("Kuantitas nol atau negatif harus false")
    void testKuantitasInvalid() {
        assertFalse(ValidationUtils.isValidKuantitas(0));
        assertFalse(ValidationUtils.isValidKuantitas(-5));
    }

    @Test
    @DisplayName("Kuantitas positif harus true")
    void testKuantitasValid() {
        assertTrue(ValidationUtils.isValidKuantitas(1));
        assertTrue(ValidationUtils.isValidKuantitas(10));
    }

    // ========================
    // isValidKategori()
    // ========================

    @Test
    @DisplayName("Kategori null harus false")
    void testKategoriNull() {
        assertFalse(ValidationUtils.isValidKategori(null));
    }

    @Test
    @DisplayName("Kategori dengan deskripsi terlalu panjang harus false")
    void testKategoriDeskripsiTerlaluPanjang() {
        String deskripsiPanjang = "X".repeat(600);
        Kategori k = new Kategori("K01", "Elektronik", deskripsiPanjang);
        assertFalse(ValidationUtils.isValidKategori(k));
    }

    @Test
    @DisplayName("Kategori dengan nama tidak valid harus false")
    void testKategoriNamaTidakValid() {
        Kategori k = new Kategori("K02", "", "Deskripsi valid");
        assertFalse(ValidationUtils.isValidKategori(k));
    }

    @Test
    @DisplayName("Kategori valid dengan deskripsi null harus true")
    void testKategoriDeskripsiNullValid() {
        Kategori k = new Kategori("K03", "Elektronik", null);
        assertTrue(ValidationUtils.isValidKategori(k));
    }

    @Test
    @DisplayName("Kategori valid dengan deskripsi pendek harus true")
    void testKategoriValid() {
        Kategori k = new Kategori("K04", "Fashion", "Pakaian dan aksesori");
        assertTrue(ValidationUtils.isValidKategori(k));
    }

    @Test
    @DisplayName("Kategori dengan kode invalid tapi deskripsi pendek harus false")
    void testKategoriKodeInvalidDeskripsiPendek() {
        Kategori k = new Kategori("K!!", "Fashion", "Pendek");
        assertFalse(ValidationUtils.isValidKategori(k));
    }

    // ========================
    // isValidProduk()
    // ========================

    @Test
    @DisplayName("Produk null harus false")
    void testProdukNull() {
        assertFalse(ValidationUtils.isValidProduk(null));
    }

    @Test
    @DisplayName("Produk dengan kode null harus false")
    void testProdukKodeNull() {
        Produk p = new Produk(null, "Laptop", "Elektronik", 10000, 5, 2);
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk dengan kategori kosong harus false")
    void testProdukKategoriKosong() {
        Produk p = new Produk("P01", "Laptop", "", 10000, 5, 2);
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk dengan harga nol harus false")
    void testProdukHargaNol() {
        Produk p = new Produk("P02", "Laptop", "Elektronik", 0, 5, 2);
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk dengan stok negatif harus false")
    void testProdukStokNegatif() {
        Produk p = new Produk("P03", "Laptop", "Elektronik", 10000, -1, 2);
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk dengan stok minimum negatif harus false")
    void testProdukStokMinimumNegatif() {
        Produk p = new Produk("P04", "Laptop", "Elektronik", 10000, 5, -1);
        assertFalse(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk valid harus true")
    void testProdukValid() {
        Produk p = new Produk("P05", "Laptop", "Elektronik", 15000, 5, 2);
        assertTrue(ValidationUtils.isValidProduk(p));
    }

    @Test
    @DisplayName("Produk valid tapi stok minimum negatif harus false (branch terakhir)")
    void testProdukValidTapiStokMinimumNegatif() {
        Produk p = new Produk("P06", "Laptop", "Elektronik", 15000, 10, -2);
        assertFalse(ValidationUtils.isValidProduk(p));
    }
}
