(ns cloudfront.views.common
  (:use [noir.core :only [defpartial]]
        [noir.options :only [dev-mode?]]
        [net.cgrand.enlive-html :only [deftemplate content attr= at clone-for set-attr]])
  (:require [clojure.java.io :as io]))

(def resources-directory "resources/public/")

(def html-watcher nil)
(def css-watcher  nil)
(def js-watcher   nil)

(defn get-resource
  [filename]
  (io/as-file filename))

(def html-filename #(str resources-directory "html/" %))
(def css-filename  #(str resources-directory "css/" %))
(def js-filename   #(str resources-directory "js/" %))

(def html-file #(get-resource (html-filename %)))
(def css-file  #(get-resource (css-filename %)))
(def js-file   #(get-resource (js-filename %)))

(defn include-css
  [styles]
  (if (empty? styles)
    identity
    #(at %
         [:link (attr= :type "text/css")]
         (clone-for [style styles] (set-attr :href style)))))

(defn include-js
  [scripts]
  (if (empty? scripts)
    identity
    #(at %
         [:div#scripts :script]
         (clone-for [script scripts] (set-attr :src script)))))

(deftemplate layout
  (html-file "layout.html")
  [& {:keys [scripts stylesheets body]}]

  [:body]        (set-attr :onload (if true
                                     "cloudfront.main.start('true');"
                                     "cloudfront.main.start();"))
  
  [:head]        (include-css (map css-filename stylesheets))
  [:div#wrapper] (content body)
  [:div#scripts] (include-js (map js-filename scripts)))

(comment
 (defpartial layout [& content]
            (html5
              [:head
               [:title "cloudfront"]
               (include-css "/css/reset.css")]
              [:body
               [:div#wrapper
                content]])))
