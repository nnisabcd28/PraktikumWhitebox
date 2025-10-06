package com.praktikum.whitebox.service;

import com.praktikum.whitebox.model.Produk;
import com.praktikum.whitebox.repository.RepositoryProduk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Lengkap ServiceInventaris - 100% Coverage")
public class ServiceInventarisTest {

    @Mock
    private RepositoryProduk mockRepositoryProduk;

    private ServiceInventaris serviceInventaris;
    private Produk produkAktif;
    private Produk produkNonAktif;

    @BeforeEach
    void setUp() {
        serviceInventaris = new ServiceInventaris(mockRepositoryProduk);
        produkAktif = new Produk("PROD001", "Laptop Gaming", "Elektronik", 15000000, 10, 5);
        produkNonAktif = new Produk("PROD002", "Mouse Wireless", "Elektronik", 500000, 10, 3);
        produkNonAktif.setAktif(false);
    }

    // ======================================================
    // ================ tambahProduk() =======================
    // ======================================================

    @Test
    @DisplayName("Tambah produk berhasil - semua kondisi valid")
    void testTambahProdukBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        when(mockRepositoryProduk.simpan(produkAktif)).thenReturn(true);

        boolean hasil = serviceInventaris.tambahProduk(produkAktif);

        assertTrue(hasil);
        verify(mockRepositoryProduk).simpan(produkAktif);
    }

    @Test
    @DisplayName("Tambah produk gagal - produk sudah ada")
    void testTambahProdukGagalSudahAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));

        boolean hasil = serviceInventaris.tambahProduk(produkAktif);

        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).simpan(any());
    }

    @Test
    @DisplayName("Tambah produk gagal - produk invalid")
    void testTambahProdukInvalid() {
        Produk invalid = new Produk(null, "", "", -1, -5, -2);
        boolean hasil = serviceInventaris.tambahProduk(invalid);
        assertFalse(hasil);
    }

    // ======================================================
    // ================ hapusProduk() ========================
    // ======================================================

    @Test
    @DisplayName("Hapus produk gagal - kode kosong")
    void testHapusProdukKodeKosong() {
        assertFalse(serviceInventaris.hapusProduk(""));
    }

    @Test
    @DisplayName("Hapus produk gagal - produk tidak ditemukan")
    void testHapusProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        assertFalse(serviceInventaris.hapusProduk("PROD001"));
    }

    @Test
    @DisplayName("Hapus produk gagal - stok masih ada")
    void testHapusProdukStokMasihAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        assertFalse(serviceInventaris.hapusProduk("PROD001"));
    }

    @Test
    @DisplayName("Hapus produk berhasil - stok habis")
    void testHapusProdukBerhasil() {
        produkAktif.setStok(0);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        when(mockRepositoryProduk.hapus("PROD001")).thenReturn(true);

        boolean hasil = serviceInventaris.hapusProduk("PROD001");

        assertTrue(hasil);
        verify(mockRepositoryProduk).hapus("PROD001");
    }

    // ======================================================
    // ================ updateStok() ========================
    // ======================================================

    @Test
    @DisplayName("Update stok gagal - kode kosong")
    void testUpdateStokKodeKosong() {
        assertFalse(serviceInventaris.updateStok("", 5));
    }

    @Test
    @DisplayName("Update stok gagal - stok negatif")
    void testUpdateStokNegatif() {
        assertFalse(serviceInventaris.updateStok("PROD001", -5));
    }

    @Test
    @DisplayName("Update stok gagal - produk tidak ditemukan")
    void testUpdateStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        assertFalse(serviceInventaris.updateStok("PROD001", 10));
    }

    @Test
    @DisplayName("Update stok berhasil")
    void testUpdateStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        when(mockRepositoryProduk.updateStok("PROD001", 10)).thenReturn(true);

        boolean hasil = serviceInventaris.updateStok("PROD001", 10);

        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 10);
    }

    // ======================================================
    // ================ masukStok() =========================
    // ======================================================

    @Test
    @DisplayName("Masuk stok gagal - kode kosong")
    void testMasukStokKodeKosong() {
        assertFalse(serviceInventaris.masukStok("", 5));
    }

    @Test
    @DisplayName("Masuk stok gagal - jumlah <= 0")
    void testMasukStokJumlahTidakValid() {
        assertFalse(serviceInventaris.masukStok("PROD001", 0));
    }

    @Test
    @DisplayName("Masuk stok gagal - produk tidak ditemukan")
    void testMasukStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        assertFalse(serviceInventaris.masukStok("PROD001", 5));
    }

    @Test
    @DisplayName("Masuk stok gagal - produk tidak aktif")
    void testMasukStokProdukTidakAktif() {
        when(mockRepositoryProduk.cariByKode("PROD002")).thenReturn(Optional.of(produkNonAktif));
        assertFalse(serviceInventaris.masukStok("PROD002", 5));
    }

    @Test
    @DisplayName("Masuk stok berhasil")
    void testMasukStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        when(mockRepositoryProduk.updateStok("PROD001", 15)).thenReturn(true);

        assertTrue(serviceInventaris.masukStok("PROD001", 5));
        verify(mockRepositoryProduk).updateStok("PROD001", 15);
    }

    // ======================================================
    // ================ keluarStok() ========================
    // ======================================================

    @Test
    @DisplayName("Keluar stok berhasil - stok mencukupi")
    void testKeluarStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        when(mockRepositoryProduk.updateStok("PROD001", 5)).thenReturn(true);

        assertTrue(serviceInventaris.keluarStok("PROD001", 5));
        verify(mockRepositoryProduk).updateStok("PROD001", 5);
    }

    @Test
    @DisplayName("Keluar stok gagal - update repository false (branch terakhir)")
    void testKeluarStokUpdateGagal() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        when(mockRepositoryProduk.updateStok("PROD001", 5)).thenReturn(false);

        boolean hasil = serviceInventaris.keluarStok("PROD001", 5);

        assertFalse(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 5);
    }

    @Test
    @DisplayName("Keluar stok gagal - stok tidak cukup")
    void testKeluarStokTidakCukup() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        assertFalse(serviceInventaris.keluarStok("PROD001", 20));
    }

    @Test
    @DisplayName("Keluar stok gagal - produk tidak ditemukan")
    void testKeluarStokTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        assertFalse(serviceInventaris.keluarStok("PROD001", 5));
    }

    @Test
    @DisplayName("Keluar stok gagal - produk tidak aktif")
    void testKeluarStokProdukTidakAktif() {
        when(mockRepositoryProduk.cariByKode("PROD002")).thenReturn(Optional.of(produkNonAktif));
        assertFalse(serviceInventaris.keluarStok("PROD002", 5));
    }

    @Test
    @DisplayName("Keluar stok gagal - jumlah <= 0")
    void testKeluarStokJumlahTidakValid() {
        assertFalse(serviceInventaris.keluarStok("PROD001", 0));
    }

    // ======================================================
    // ================ hitungTotalNilaiInventaris() =========
    // ======================================================

    @Test
    @DisplayName("Hitung total nilai inventaris hanya produk aktif")
    void testHitungTotalNilaiInventaris() {
        Produk p1 = new Produk("A", "Barang1", "Elektronik", 1000, 5, 1);
        Produk p2 = new Produk("B", "Barang2", "Elektronik", 2000, 3, 1);
        p2.setAktif(false);
        Produk p3 = new Produk("C", "Barang3", "Elektronik", 3000, 10, 1);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(p1, p2, p3));

        double total = serviceInventaris.hitungTotalNilaiInventaris();
        assertEquals((1000 * 5) + (3000 * 10), total);
    }

    // ======================================================
    // ================ hitungTotalStok() ====================
    // ======================================================

    @Test
    @DisplayName("Hitung total stok semua produk aktif")
    void testHitungTotalStok() {
        Produk p1 = new Produk("A", "Barang1", "Elektronik", 1000, 5, 1);
        Produk p2 = new Produk("B", "Barang2", "Elektronik", 2000, 3, 1);
        p2.setAktif(false);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(p1, p2));

        int totalStok = serviceInventaris.hitungTotalStok();
        assertEquals(5, totalStok);
    }

    // ======================================================
    // ================ cariProduk...() ======================
    // ======================================================

    @Test
    @DisplayName("Cari produk by kode valid")
    void testCariProdukByKodeValid() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkAktif));
        Optional<Produk> result = serviceInventaris.cariProdukByKode("PROD001");
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Cari produk by kode kosong")
    void testCariProdukByKodeKosong() {
        Optional<Produk> result = serviceInventaris.cariProdukByKode("");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Cari produk by nama")
    void testCariProdukByNama() {
        when(mockRepositoryProduk.cariByNama("Laptop")).thenReturn(Collections.singletonList(produkAktif));
        List<Produk> result = serviceInventaris.cariProdukByNama("Laptop");
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Cari produk by kategori")
    void testCariProdukByKategori() {
        when(mockRepositoryProduk.cariByKategori("Elektronik")).thenReturn(Collections.singletonList(produkAktif));
        List<Produk> result = serviceInventaris.cariProdukByKategori("Elektronik");
        assertEquals(1, result.size());
    }

    // ======================================================
    // ================ getProdukStokHabis() ================
    // ======================================================

    @Test
    @DisplayName("Get produk stok habis")
    void testGetProdukStokHabis() {
        Produk habis = new Produk("K001", "Kabel", "Elektronik", 5000, 0, 1);
        when(mockRepositoryProduk.cariProdukStokHabis()).thenReturn(Collections.singletonList(habis));

        List<Produk> result = serviceInventaris.getProdukStokHabis();
        assertEquals(1, result.size());
        assertEquals("K001", result.get(0).getKode());
    }

    // ======================================================
    // ================ getProdukStokMenipis() ==============
    // ======================================================

    @Test
    @DisplayName("Get produk stok menipis")
    void testGetProdukStokMenipis() {
        Produk menipis = new Produk("P09", "Flashdisk", "Elektronik", 10000, 1, 5);
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(Collections.singletonList(menipis));

        List<Produk> result = serviceInventaris.getProdukStokMenipis();
        assertEquals(1, result.size());
        assertEquals("P09", result.get(0).getKode());
    }
}
