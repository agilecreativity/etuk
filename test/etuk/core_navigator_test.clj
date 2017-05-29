(ns etuk.core-navigator-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all :as t]))

(t/testing "::element-type parameter"
  (is (= true (s/valid? :etuk.core-navigator/element-type :id)))
  (is (= false (s/valid? :etuk.core-navigator/element-type :invalid-key))))
