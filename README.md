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

## License

Copyright (C) 2011 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

