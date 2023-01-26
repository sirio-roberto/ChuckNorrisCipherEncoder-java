# ChuckNorrisCipherEncoder-java

This encoder will basically get each character of the input string, convert to its binery representation using ASCII pattern with 7 digits and create two consecutive blocks are used to produce a series of the same value bits (only 1 or0 values):
  First block: it is always 0 or 00. If it is 0, then the series contains 1, if not, it contains 0
  Second block: the number of 0 in this block is the number of bits in the series
  
  
Let's take a simple example with a message which consists of only one character 'C'. The 'C' symbol in binary is represented as 1000011, so with Chuck Norris technique this gives:
  0 0 (the first series consists of only a single 1);
  00 0000 (the second series consists of four 0);
  0 00 (the third consists of two 1)
  So 'C' is coded as: 0 0 00 0000 0 00
  
  Another example is the sentence 'Hi <3' (ignore the single quotes), which can be encoded to  0 0 00 00 0 0 00 000 0 00 00 0 0 0 00 00 0 0 00 0 0 0 00 000000 0 0000 00 000 0 00 00 00 0 00
 
This applicaton also decodes encoded strings that were encoded using this app.
