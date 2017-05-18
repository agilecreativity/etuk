(ns etuk.core-navigator
  (:require [webica.core :as w])
  (:require [clojure.spec.alpha :as s])
  (:require [clojure.string :as str]
            [webica
             [by :as by]
             [core :as w]
             [expected-conditions :as ec]
             [remote-web-driver :as browser]
             [web-driver-wait :as wdriver]
             [web-element :as element]]))

;; Add basic Clojure spec
(s/def ::element-type #{:id
                        :xpath
                        :css-selector
                        :name
                        :tag-name
                        :link-text
                        :partial-link-text
                        :class-name})

(defn ^:private find-web-element
  "Locate an element identified by a given `locator` and return it."
  [wdriver wait-fn locator]
  (.until wdriver (wait-fn locator))
  (browser/find-element locator))

;; Note: originally, this method is private!
(defn find-element-by
  "Return the element located by a given type of expression"
  [type expr]
  (if-not (s/valid? ::element-type type)
    (throw (ex-info (s/explain-str ::element-type type)
                    (s/explain-data ::element-type type))))
  (cond
    (= type :id)                (by/id expr)
    (= type :xpath)             (by/xpath expr)
    (= type :css-selector)      (by/css-selector expr)
    (= type :name)              (by/name expr)
    (= type :tag-name)          (by/tag-name expr)
    (= type :link-text)         (by/link-text expr)
    (= type :partial-link-text) (by/partial-link-text expr)
    (= type :class-name)        (by/class-name expr)))

(defn locate-element
  "Locate a given element having a given `type` matching `expr` using the given `wfn` function.

  Examples:
  (locate-element wdriver ec/element-to-be-clickable :css-selector \"#some-id\")"
  [wdriver wfn type expr]
  (find-web-element wdriver wfn (find-element-by type expr)))

(defmacro navigate
  "Locate and a given element having a given type matching a given expression.

  a) with action that need no argument e.g. (element/click (browser/find-element (by/css-selector \"#.button-full\")))
  (navigate :wdriver  wdriver
            :wfn      ec/presence-of-element-located
            :type     :css-selector
            :expr     \".button-full\"
            :act-name element/click
            :act-arg  nil)

  b) with action that need argument e.g. (element/send-keys (browser/find-element (by/css-selector \"#login_email\"))) \"john@mailinator.com\")

  (navigate :wdriver  wdriver
            :wfn      ec/presence-of-element-located
            :type     :css-selector
            :expr     \"#login_email\"
            :act-name element/send-keys
            :act-arg  \"john@mailinator.com\")"
  [& {:keys [wdriver
             wfn
             type
             expr
             act-name
             act-arg]}]

  `(if-not (nil? ~act-arg)
     (~act-name (locate-element ~wdriver ~wfn ~type ~expr) ~act-arg)
     (~act-name (locate-element ~wdriver ~wfn ~type ~expr))))
