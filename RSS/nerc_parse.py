from bs4 import BeautifulSoup
import bs4


def parse_nerc(i):
	xml = BeautifulSoup(open('temp/nerc.txt'), "xml")
	for item in xml.entities:
		if type(item) == bs4.element.Tag:
			print item['type']
			name = str(item.references)
			name = name[name.find('<!--') + len('<!--'): name.find('-->')]
			print name
	
parse_nerc(0)
