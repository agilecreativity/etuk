(defproject etuk "0.1.2-SNAPSHOT"
  :description "Selenium web driver wrapper to automate commonly used apis."
  :resource-paths ["src" "resources"]
  :scm {:url "https://github.com/agilecreativity/etuk"}
  :exclusions []
  :source-paths ["src" "test"]
  :repositories [["clojars" {:url "https://repo.clojars.org/"}]
                 ["maven-central" {:url "https://repo1.maven.org/maven2"}]]
  :mirrors []
  :url "http://github.com/agilecreativity/etuk"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/data.xml "0.2.0-alpha2"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/spec.alpha "0.1.123"]
                 [webica "3.4.0-beta2-clj0"]
                 [org.seleniumhq.selenium/selenium-java "3.4.0"]
                 [org.seleniumhq.selenium/selenium-server "3.4.0"]
                 [org.seleniumhq.selenium/selenium-firefox-driver "3.4.0"]
                 [sparkfund/boot-lein-generate "0.3.0"]
                 [me.raynes/fs "1.4.6"]
                 [adzerk/boot-test "1.2.0" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]
                 [easy-config "0.1.2"]])
