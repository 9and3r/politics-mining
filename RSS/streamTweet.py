# Es necesario instalar las librerias tika y twitter, ambas disponibles con pip install
# En ubuntu se puede instalar usando:
# sudo apt-get install python-twitter

import sys
import twitter
import json
from save_filename import *

if len(sys.argv) != 2:
	sys.exit("\nError en numero de parametros\n\nPara ejecutar este script debes usar un parametro:\n1-Carpeta de ficheros de salida")

def procesarTweet(tweet, folder):
	text = tweet.text
	# Se cambian las menciones por los nombres. Por ejemplo @pedrosanchez es sustituido por Pedro Sanchez
	for mention in tweet.user_mentions:
		text = text.replace('@' + mention.screen_name, mention.name)
	title = tweet.user.name + text[:15]

	# Guardar los datos en el fichero
	f = open(folder + format_filename(title) +'.txt', 'w')
	f.write(text.encode('utf-8') + '\n')
	f.write('##########\n')
	f.write('title=' + title.encode('utf-8') + '\n')
	f.write('domain=twitter.com\n')
	f.close()


# Primero ir a  http://dev.twitter.com/apps/new crear una nueva app con tu cuenta y conseguir estas claves
CONSUMER_KEY = 'wdqmdtdvbVl6EXcXZYhjMZ9TX'
CONSUMER_SECRET = 'nVynzo3KIAmKP9pvM9EEvW0QwVlLJlVeFa6oBXAHO9UTB0ldm5'
OAUTH_TOKEN = '709077519836844032-JAVW07VigfZKtbvFvzU9Vn08KAR8hip'
OAUTH_TOKEN_SECRET = 'n51eeqXE5Ru4XRAknleldnGW7YyHgDHgyeMavE3DSD4d8'

api = twitter.Api(consumer_key=CONSUMER_KEY,
                  consumer_secret=CONSUMER_SECRET,
                  access_token_key=OAUTH_TOKEN,
                  access_token_secret=OAUTH_TOKEN_SECRET)

# Pagina par obtener las ids http://gettwitterid.com/

ids = { 'Mariano Rajoy' : '343447873',
	'Pedro Sanchez': '68740712',
	'Albert Rivera': '108994652',
	'Pablo Iglesias': '158342368',
	'Alberto Garzon': '11904592'}

for name in ids:
	# count 200 maximo
	statuses = api.GetUserTimeline(ids[name], count=10)
	for tweet in statuses:	
		procesarTweet(tweet, sys.argv[1])
	

