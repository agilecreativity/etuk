(def project 'etuk)
(def version "0.1.2-SNAPSHOT")

(set-env! :resource-paths #{"src" "resources"}
          :source-paths   #{"src" "test"}
          :dependencies   '[[org.clojure/clojure "1.9.0-alpha17"]
                            [org.clojure/data.xml "0.2.0-alpha2"]
                            [org.clojure/data.csv "0.1.4"]
                            [org.clojure/tools.cli "0.3.5"]
                            [org.clojure/spec.alpha "0.1.123"]
                            [webica "3.4.0-clj0"]
                            [org.seleniumhq.selenium/selenium-java "3.4.0"]
                            [org.seleniumhq.selenium/selenium-server "3.4.0"]
                            [org.seleniumhq.selenium/selenium-firefox-driver "3.4.0"]
                            [me.raynes/fs "1.4.6"]
                            [easy-config "0.1.2"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [adzerk/bootlaces "0.1.13" :scope "test"]])

(task-options!
 pom {:project     project
      :version     version
      :description "Selenium web driver wrapper to automate commonly used apis."
      :url         "http://github.com/agilecreativity/etuk"
      :scm         {:url "https://github.com/agilecreativity/etuk"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.bootlaces :refer :all])

(bootlaces! version)

(deftask clj-dev
  "Clojure REPL for CIDER"
  []
  (comp
    (cider)
    (repl :server true)
    (wait)))

(deftask cider-boot
  "Cider boot params task"
  []
  (clj-dev))

;; equivalence of `lein run'
(require 'etuk.examples.download-selenium)
(deftask download-selenium
  "Download Selenium jar file"
  []
  (with-pass-thru _
    (etuk.examples.download-selenium/-main)))

(require 'etuk.examples.login-github)
(deftask login-github
  "Login to Github.com"
  []
  (with-pass-thru _
    (etuk.examples.login-github/-main)))

(require 'etuk.examples.google-search)
(deftask google-search
  "Search for some text on Google"
  []
  (with-pass-thru _
    (etuk.examples.google-search/-main "Github Emacs Trending")))
