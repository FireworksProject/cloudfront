# cloudfront

This is a web application that provides a front-end for cloudmill. 

## Usage

```bash
lein run
open http://localhost:8080
```

NOTE: Currently the only use cases provided are create/destroy couchdb
virtual machines. Be sure not to close your cloudfront tab, or you'll
loose state. Also, pay attention to the alert dialog box. It will tell
you the IP address of your couchdb instance.

Each of the operations are idempotent. So, for example, if you hit 
"Create" and then miss what the dialog said about the IP address of 
the new instance you can simply hit "Create" again. The new dialog 
window will pop up much more quicklky this time. Similarly, hitting
"Destroy" multiple times in a row wont cause issues.

## TODO

1. issue #1

## License

Copyright (C) 2011 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

