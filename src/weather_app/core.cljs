(ns ^:figwheel-hooks weather-app.core
  (:require [reagent.dom :as rd]
            [ajax.core :refer [GET]]
            [tick.core :as tick]))

(enable-console-print!)

(def days
  ["Mon" "Tue" "Wed" "Thu" "Fri" "Sat" "Sun"])

(def seven-day-forecast-stub
  ["Clear" "Clear" "Clouds" "Rain" "Thunderstorm" "Drizzle" "Snow"])

(def seven-day-forecast-temps-stub
  ["30" "32" "28" "27" "25" "20" "18"])

(defn get-forecasts
  "Calls weather api and returns response from API using current time and provided co-ords."
  [lat long]
  (GET (str "https://api.openweathermap.org/data/2.5/onecall?lat="
            lat
            "&lon="
            long
            "&exclude=minutely,hourly&appid=cfba2d20b6e35bb75e4b4f5bb95666eb")))

(defn daily-forecast-component
  []
  [:div
   [:span {:class "col-md-8"}
    [:div [:i {:class "wi wi-day-sunny" } " 27"[:i {:class "wi wi-celsius"}]]]
    [:div
     [:span {:class "col-md-2"}
     [:i {:class "wi wi-thermometer"} " 20"] [:i {:class "wi wi-celsius"}]]
    [:span {:class "col-md-2"}
     [:i {:class "wi wi-thermometer-exterior"} " 20" [:i {:class "wi wi-celsius"}]]]]]
   [:span {:class "col-md-4"}
    [:div {:class "wi wi-rain"}  " %"]
    [:div "kph"]
    [:div
     [:i {:class "wi wi-humidity"} " 32%"]]]]
  )

(defn epoch->day
  "converts a UNIX epoch to human readable time, returns the value of the day"
  [epoch]
  (tick/day-of-week (tick/date epoch)))

(defmulti forecast->glyph
  "Takes a map containing a weather or wind direction forecast, matches it to the correct glyph. Despatches on string value of the map's key"
  ()
  )

;(defmethod forecast->glyph [forecast]
;  "Rain")

(defn seven-day-forecast
  [forecasts]
  "")

(defn refresh-button
  "Reloads the component by calling init"
  ;probably not the best way of implementing a reload, especially when the dropdown comes into play
  []
  )

(defn weekly-forecast-component
  []
  [:table {:class "table"}
   [:tr (for [d days] [:th d])]
   ;[:tr (for [f seven-day-forecast-stub] [:td [:i {:class }]])]
   [:tr (for [t seven-day-forecast-temps-stub] [:td
                                                [:i {:class "wi wi-thermometer"}]
                                                t
                                                [:i {:class "wi wi-celsius"}]])]
   [:tr (for [t seven-day-forecast-temps-stub] [:td
                                                [:i {:class "wi wi-thermometer-exterior"}]
                                                t
                                                [:i {:class "wi wi-celsius"}]])]
   ])

(defn weather-component
  []
  (let [forecasts (get-forecasts 55.86 -4.25)]
    [:div {:class "well"}
     [:h "Today's Weather" (refresh-button)]
     [:div (daily-forecast-component)]
     [:div (weekly-forecast-component)]

     ]))

(defn ^:after-load ^:export init
  []
  (rd/render [weather-component]
             (.getElementById js/document "app")))
