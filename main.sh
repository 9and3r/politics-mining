cd RSS
python streamTweet.py ../Resultados/
python rss_download.py http://ep00.epimg.net/rss/elpais/portada.xml ../Resultados/
python rss_download.py http://www.abc.es/rss/feeds/abc_EspanaEspana.xml ../Resultados/
python nerc_parse.py ../Resultados/
cd ../SentimentAnalysis
java -jar ./SentimentAnalysis.jar -i ../Resultados -o ../Analizados
cd ..
java Visualization.jar ./Resultados
