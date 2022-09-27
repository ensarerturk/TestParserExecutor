# TestParserExecutor
Dısarıdan belirli formatta olan test dosyasını post istegi ile end-point’ten belirli formatta(.zip) alınmaktadır. Alınan
test dosyası sunucu görevi gören Docker Container’da referans gösterilen dosya yoluna
kaydedilmektedir. Alınan test dosyasına her noktadan kolaylıkla erişilebilecek olan github’a
yüklenmektedir. Çok çeşitli testlerin çalıştırılabilecegi bir ortam olan Jenkins’te testin çalışması
için bir tetikleme gerçekletirilir.Gönderilen tetikleme ile testin başlayabilmesi
için github repo’dan referans alınmalı ve teste göre konfigürasyon ayarları otomatik olarak
yapılandırılmaktadır. Jenkins’te çalıştırılan testin sonunda elde edilen sonuçlar otomatik
olarak alınarak istenilen formatta database’e kaydedilmektedir. Kullanıcı, testin başlangıcında
gönderdigi test dosyasının sonuçlarına istediği sonuç detayları ile ulaşabilmelidir. Tüm bu 
özellikleri ise tek sistemde ayaga kaldırmak, sistem bağımlılığına takılmamak için Docker Container kullanılmaktadır.
