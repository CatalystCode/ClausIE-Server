# ClausIE-Server

## Usage

First, run the ClausIE-Server via docker:

```sh
docker run -d -p 4567:4567 cwolff/clausieserver
```

Second, prepare an input file with text to process via OpenIE. The file
should have one sentence per line.

```txt
AE remained in Princeton until his death.
AE is a scientist of the 20th century.
AE has won the Nobel Prize in 1921.
```

Next, call the ClausIE-Server. The file with the input text should be passed
as `multipart/form-data` under the form key `upload`:

```sh
curl -F "upload=@sentences.txt" "http://localhost:4567/openie/form"
```

The service will respond with the OpenIE relations extracted from the sentences
in JSON-object per line format. Note that one sentence may produce multiple
relations:

```jsonl
{"argument":"in Princeton until his death","line":0,"subject":"AE","relation":"remained"}
{"argument":"in Princeton","line":0,"subject":"AE","relation":"remained"}
{"argument":"death","line":0,"subject":"his","relation":"has"}
{"argument":"a scientist of the 20th century","line":1,"subject":"AE","relation":"is"}
{"argument":"a scientist","line":1,"subject":"AE","relation":"is"}
{"argument":"the Nobel Prize in 1921","line":2,"subject":"AE","relation":"has won"}
```

## License

* This distribution includes libraries of [Clausie v. 0.0.1](https://www.mpi-inf.mpg.de/departments/databases-and-information-systems/software/clausie/) which is distributed under the [Attribution-ShareAlike (ver. 3.0 or later)](http://creativecommons.org/licenses/by-sa/3.0/legalcode)

* This distribution includes libraries of the [Stanford Parser v. 2.0.5](http://www-nlp.stanford.edu/software/lex-parser.shtml) which is licensed under the [GNU General Public License (v2 or later)](http://www.gnu.org/licenses/gpl-2.0.html)
