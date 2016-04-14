java -jar ./ixa-pipes/ixa-pipe-tok.jar server -p 9000 -l es &
java -jar ./ixa-pipes/ixa-pipe-pos.jar server -p 9001 -l es -m modeloak/morph-models-15.0/es/es-pos-perceptron-autodict01-ancora-2.0.bin -lm modeloak/morph-models-15.0/es/es-lemma-perceptron-ancora-2.0.bin &
java -jar ./ixa-pipes/ixa-pipe-nerc.jar server -p 9002 -l es -m ./modeloak/nerc-models-1.5.4/es/es-clusters-conll02.bin &
