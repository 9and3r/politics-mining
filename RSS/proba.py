#!/usr/bin/env python
# -*- coding: utf-8 -*-
import array

#Entrada: String con o sin espacios|persona a identificar
#Salida: nombre completo de la persona identificada
def who_is_this(keyword):
	arr=[]

	inp = open ("./Datuak/Entitateak/JSON-NE-NameSearch.json")
	name = ''
	surname = ''
	separado = keyword.split(' ')
	#en caso de que el string de entrada incluya palabras separadas, trabajaremos con la última
	if(len(separado) > 1):
		keyword = separado[len(separado)-1]
	#por cada línea buscaremos alguna coincidencia para guardarla
	for line in inp.readlines():
	 	lineinfo = []
	 	lineinfo=line.split(',')#Estructura nombre|nombre2(en caso de que sea compuesto)|apellido| partido|puesto en el partido|puesto en el gobierno.

	 	if(lineinfo[0]==keyword): #comparamos si el keyword coincide con la línea
	 		if(lineinfo[1]==''):#si tiene nombre completo
	 			name = lineinfo[0]

	 		else:#si tiene nombre completo
	 			name = lineinfo[0]+' '+lineinfo[1]

	 		surname = lineinfo[2]

	 	else:#si no ha habido coincidencia del keyword con el nombre probamos lo mismo co el apellido
	 		if(keyword in lineinfo[2]):
	 			if(lineinfo[1]==''):
		 			name = lineinfo[0]

		 		else:
		 			name = lineinfo[0]+' '+lineinfo[1]
	 		
	 			surname = lineinfo[2]
	 		
	print(name + ' ' + surname) 	
	 	
#arr[0] = arr[0].encode('UTF-8')
who_is_this("Iñigo")
# -*- coding: utf-8 -*-