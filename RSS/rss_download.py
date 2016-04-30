import sys
import os
import urllib2
from bs4 import BeautifulSoup
from save_filename import *

if len(sys.argv) != 3:
	sys.exit("\nError en numero de parametros\n\nPara ejecutar este script debes usar un parametro:\n1-URL de la RSS\n2-Carpeta de ficheros de salida")

# http://www.crummy.com/software/BeautifulSoup/bs4/doc/

# Leer el codigo html
rss_xml = urllib2.urlopen(sys.argv[1]).read()

rss = BeautifulSoup(rss_xml, "xml")

# Cada item representa una noticia
for item in rss.findAll('item'):
	#print item
	page = urllib2.urlopen(item.link.get_text()).read()

	soup = BeautifulSoup(page, 'html.parser')

	# No estamos interesados en los estilos y codigos de script
	# Eliminamos todo lo relacionado con css y javascript
	for script in soup(["script", "style"]):
    		script.extract()

	# Buscar el itemprop="articleBody". Aqui se encuentra el articulo. ElPais, ElMundo, ABC parece que hacen uso de este indicador
	# https://developers.google.com/structured-data/
	#
	body = soup.find(itemprop="articleBody")
	if body:
		result = body.get_text()
		file_path = sys.argv[2] + format_filename(item.title.get_text()) + '.txt'
		f = open(file_path, 'w')
		f.write('title=' + item.title.get_text().encode('utf-8') + '\n')
		f.write('##########\n')
		f.write(result.encode('utf8'))
		f.close
	else:
		# Si el articulo no contiene articleBody no sera procesado y se imprimira en pantalla su titulo y el link
		print 'Error. No se ha encontrado articleBody. El articulo no sera procesado:'
		print item.title.get_text()
		print item.link.get_text() + '\n'
			
