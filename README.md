GİDER TAKİP UYGULAMASI PROJE RAPORU
1. Özet (Abstract)
Bu projede, bireysel harcamaların dijital ortamda düzenli, güvenli ve kategorize bir şekilde takip edilmesini sağlayan Java tabanlı bir "Gider Takip Uygulaması" geliştirilmiştir. Uygulama; kullanıcı doğrulaması yaparak kullanıcıların sisteme güvenli giriş yapabilmesini, yeni gider verilerini (tutar, tarih, açıklama, miktar) ekleyebilmesini, mevcut giderleri listelemesini ve tüm bu verilerin metin dosyasında saklanmasını sağlar.

2. Giriş ve Problem Tanımı
Harcamaların manuel yöntemlerle ya da kağıt üzerinde tutulması veri kaybına, hesaplama hatalarına, hesaplama zorluğuna neden olmaktadır. Bu projenin amacı, finansal kayıt süreçlerini dijitalleştirerek insan kaynaklı hataları minimuma indirmek, veri güvenliğini sağlamak ve kullanıcılara hızlı bir gider takip sistemi sunmaktır.

3. Literatür Taraması
Mevcut bütçe ve finans yönetimi yazılımları incelendiğinde; esnek ve genişletilebilir veri modellerinin, güçlü bir kullanıcı doğrulama (güvenlik) katmanının ve verilerin kalıcılığını sağlayan dosya/veritabanı sistemlerinin yazılım ömrünü doğrudan artırdığı görülmüştür. Bu projede de modern yazılım standartlarına uygun olarak nesne yönelimli mimari tercih edilmiştir.

4. Sistem Analizi
İşlevsel Gereksinimler (Functional Requirements):
- Sisteme kayıtlı kullanıcıların kullanıcı adı ve şifreyle giriş yapabilmesi.
- Kullanıcının harcama tutarı, harcama tarihi ve açıklama detaylarını sisteme girmesi.
- Eklenen giderlerin satır satır listelenebilmesi ve program kapatılsa dahi silinmemesi.

Sistem Kısıtları: Uygulama, yüksek donanım gücü gerektirmeden her bilgisayarda hızlıca çalışabilmesi amacıyla konsol (terminal) ekranı üzerinden çalışacak şekilde tasarlanmıştır.

5. Sistem Tasarımı (UML Diyagramları)
Uygulama, Nesne Yönelimli Programlama (OOP) prensiplerine tam uyumlu 4 temel Java sınıfından oluşmaktadır:
Main Sınıfı: Programın başlangıç noktasıdır. Konsol menüsünü ve sınıflar arası veri akışını koordine eder.
Gider Sınıfı (Model): Her bir harcamayı nesneleştiren sınıftır. Veri güvenliği için Kapsülleme (Encapsulation) ilkesi uygulanmış; değişkenler private tutularak bunlara erişim getter ve setter metotlarıyla sağlanmıştır.
Veritabanı Sınıfı: Gider verilerinin metin dosyasına yazılması ve dosyadan geri okunması gibi veri yönetim işlemlerinden sorumludur.
GuvenlikServisi Sınıfı: Kullanıcının sisteme giriş yaparken girdiği bilgileri doğrulayan güvenlik katmanıdır.

6. Veritabanı Tasarımı (ER Diyagramı)
Projede karmaşık veritabanı kurulumlarıyla uğraşmamak ve taşınabilirliği artırmak amacıyla Düz Dosya Veritabanı (Flat-File Database) mimarisi kullanılmıştır:
kullanicilar.txt: Sisteme giriş yetkisi olan kullanıcıların giriş bilgilerini saklar.
giderler.txt: Girilen harcamalara ait benzersiz ID, Açıklama, Tutar ve Tarih bilgilerini yapılandırılmış olarak satır satır saklar.

7. Gerçekleştirim (Implementation)
Projenin kodlama aşaması Java SE dili kullanılarak IntelliJ IDEA geliştirme ortamında tamamlanmıştır. Kodların tüm versiyon takipleri Git sürüm kontrol sistemiyle yapılmış ve proje GitHub üzerindeki uzak depoya (sahinyilmazhira) güvenli bir şekilde yüklenmiştir. Dosya okuma ve yazma işlemlerinde Java'nın kararlı BufferedReader ve BufferedWriter kütüphanelerinden yararlanılmıştır.

8. Test ve Doğrulama
Geliştirilen Gider Takip Uygulaması iki temel senaryo üzerinden test edilmiştir:
Kimlik Doğrulama Testi: Sisteme bilerek yanlış şifre girilmiş ve GuvenlikServisi sınıfının hatalı girişi yakalayarak sisteme erişimi başarıyla engellediği görülmüştür.
Veri Kalıcılığı Testi: Konsol üzerinden yeni harcama verileri girilmiş, program tamamen kapatıldıktan sonra giderler.txt dosyası kontrol edilmiştir. Verilerin diske başarıyla yazıldığı ve program yeniden başlatıldığında listede eksiksiz listelendiği doğrulanmıştır.

9. Sonuç ve Gelecek Çalışmalar
Nesne tabanlı programlama ilkelerine (kapsülleme, tek sorumluluk) uygun, kararlı ve veri kaybını önleyen bir Gider Takip Uygulaması başarıyla teslim edilmiştir. Projenin mimarisi genişletilmeye çok uygundur. Gelecekte bu sisteme görsel bir kullanıcı arayüzü (GUI) ve ilişkisel bir veritabanı (MySQL gibi) entegre edilerek proje daha profesyonel bir seviyeye taşınabilir.

10. Kaynakça
1. https://codegym.cc/groups/posts/bufferedreader-and-bufferedwriter
2. https://www.w3schools.com/java/java_try_catch.asp.
3. https://www.techcareer.net/courses/git-github-egitimi

Geliştirici: Hira Nur Şahinılmaz
