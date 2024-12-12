import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

class Barang {
    private String kode; // Kode barang
    private String nama; // Nama barang
    private double harga; // Harga barang

    public Barang(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }
}

class Transaksi extends Barang {
    private int noFaktur; // Nomor faktur transaksi
    private int jumlahBeli; // Jumlah barang yang dibeli
    private double total; // Total harga

    public Transaksi(int noFaktur, String kode, String nama, double harga, int jumlahBeli) {
        super(kode, nama, harga);
        this.noFaktur = noFaktur;
        this.jumlahBeli = jumlahBeli;
        this.total = harga * jumlahBeli;
    }

    public int getNoFaktur() {
        return noFaktur;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public double getTotal() {
        return total;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;
        String namaKasir = "Zakky";
        ArrayList<Transaksi> transaksiList = new ArrayList<>();
        
        // Koneksi ke database
        Connection connection = Koneksi.getConnection();
        if (connection != null) {
            System.out.println("Koneksi ke database berhasil!");
        } else {
            System.out.println("Koneksi ke database gagal!");
        }

        // LOGIN SECTION
        while (!isLoggedIn) {
            System.out.println("+-----------------------------------------------------+");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Generate random captcha
            String captcha = generateCaptcha();
            System.out.print("Captcha [" + captcha + "]: ");
            String inputCaptcha = scanner.nextLine();

            if (username.equals("pbo5") && password.equals("password") && inputCaptcha.equals(captcha)) {
                System.out.println("Login berhasil!");
                isLoggedIn = true;
            } else {
                System.out.println("Login gagal. Silakan coba lagi.");
            }
        }

        // MAIN SECTION
        System.out.println("Selamat Datang di Supermarket ABC");
        boolean ulang = true;
        while (ulang) {
            try {
                // Menampilkan tanggal dan waktu
                Date sekarang = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
                String tanggalWaktu = sdf.format(sekarang);
                System.out.println("Tanggal dan Waktu: " + tanggalWaktu);

                System.out.println("Pilih menu:");
                System.out.println("1. Tambah Transaksi");
                System.out.println("2. Lihat Transaksi");
                System.out.println("3. Ubah Transaksi");
                System.out.println("4. Hapus Transaksi");
                System.out.println("5. Keluar");

                int pilihan = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (pilihan) {
                    case 1:
                        // Tambah Transaksi
                        System.out.print("Masukkan No Faktur: ");
                        int noFaktur = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        System.out.print("Masukkan Kode Barang: ");
                        String kodeBarang = scanner.nextLine();

                        System.out.print("Masukkan Nama Barang: ");
                        String namaBarang = scanner.nextLine();

                        System.out.print("Masukkan Harga Barang: ");
                        double hargaBarang = scanner.nextDouble();

                        System.out.print("Masukkan Jumlah Beli: ");
                        int jumlahBeli = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character

                        Transaksi transaksi = new Transaksi(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli);
                        transaksiList.add(transaksi);

                        System.out.println("Transaksi berhasil ditambahkan!");
                        break;
                    case 2:
                        // Lihat Transaksi
                        if (transaksiList.isEmpty()) {
                            System.out.println("Tidak ada transaksi!");
                        } else {
                            for (int i = 0; i < transaksiList.size(); i++) {
                                Transaksi transaksiLihat = transaksiList.get(i);
                                System.out.println("+----------------------------------------------------+");
                                System.out.println("No. Faktur    : " + transaksiLihat.getNoFaktur());
                                System.out.println("Kode Barang   : " + transaksiLihat.getKode());
                                System.out.println("Nama Barang   : " + transaksiLihat.getNama());
                                System.out.println("Harga Barang : " + transaksiLihat.getHarga());
                                System.out.println("Jumlah Beli   : " + transaksiLihat.getJumlahBeli());
                                System.out.println("TOTAL         : " + transaksiLihat.getTotal());
                                System.out.println("+----------------------------------------------------+");
                                System.out.println("Kasir         : " + namaKasir);
                                System.out.println("+----------------------------------------------------+");
                            }
                        }
                        break;
                    case 3:
                        // Ubah Transaksi
                        if (transaksiList.isEmpty()) {
                            System.out.println("Tidak ada transaksi!");
                        } else {
                            System.out.print("Masukkan No Faktur transaksi yang ingin diubah: ");
                            int noFakturUbah = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            for (int i = 0; i < transaksiList.size(); i++) {
                                Transaksi t = transaksiList.get(i);
                                if (t.getNoFaktur() == noFakturUbah) {
                                    System.out.print("Masukkan Kode Barang baru: ");
                                    String kodeBarangUbah = scanner.nextLine();

                                    System.out.print("Masukkan Nama Barang baru: ");
                                    String namaBarangUbah = scanner.nextLine();

                                    System.out.print("Masukkan Harga Barang baru: ");
                                    double hargaBarangUbah = scanner.nextDouble();

                                    System.out.print("Masukkan Jumlah Beli baru: ");
                                    int jumlahBeliUbah = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline character

                                    transaksiList.set(i, new Transaksi(noFakturUbah, kodeBarangUbah, namaBarangUbah, hargaBarangUbah, jumlahBeliUbah));
                                    System.out.println("Transaksi berhasil diubah!");
                                    break;
                                }
                            }
                        }
                        break;
                    case 4:
                        // Hapus Transaksi
                        if (transaksiList.isEmpty()) {
                            System.out.println("Tidak ada transaksi!");
                        } else {
                            System.out.print("Masukkan No Faktur transaksi yang ingin dihapus: ");
                            int noFakturHapus = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character

                            for (int i = 0; i < transaksiList.size(); i++) {
                                Transaksi t = transaksiList.get(i);
                                if (t.getNoFaktur() == noFakturHapus) {
                                    transaksiList.remove(i);
                                    System.out.println("Transaksi berhasil dihapus!");
                                    break;
                                }
                            }
                        }
                        break;
                    case 5:
                        // Keluar
                        ulang = false;
                        if (connection != null) {
                        try {
                            connection.close();
                            System.out.println("Koneksi database ditutup");
                        } catch (SQLException e) {
                            System.out.println("Gagal menutup koneksi database: " + e.getMessage());
                         }
    }
                        
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Silakan masukkan data yang benar.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close(); // Menutup scanner setelah selesai
        System.out.println("Terima kasih telah menggunakan program ini.");
    }

    // Method untuk menghasilkan captcha acak
    private static String generateCaptcha() {
        Random random = new Random();
        int captchaLength = 5; // Panjang captcha
        StringBuilder captcha = new StringBuilder(captchaLength);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        for (int i = 0; i < captchaLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            captcha.append(characters.charAt(randomIndex));
        }
        
        return captcha.toString();
    }
}
