<?php

$in = fopen('../../res/lexique.en.txt', 'r');
$out = fopen('../../res/lexique.en_parsed.txt', 'c');

while ($line = rtrim(fgets($in), "\n")) {
	$line = explode(' ', $line);
	$to_write = $line[count($line)-1];
	for ($i = 0; $i < count($line)-1; ++$i) {
		$to_write .= ' '.$line[$i];
	}
	fwrite($out, $to_write."\n");
}

fclose($in);
fclose($out);