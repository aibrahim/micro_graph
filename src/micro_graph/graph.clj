(ns micro-graph.graph)

(defprotocol IGraph
  (graph-vertices [this])
  (graph-edges [this])
  (graph-add-vertex [this vertex])
  (graph-add-edge [this edge])
  (graph-adj-mat [this])
  (graph-bfs [this start]))

(extend-type clojure.lang.IPersistentMap
  IGraph
  (graph-vertices [this] (-> this keys vec))
  (graph-edges [this] (->> this
                           (mapcat
                            (fn [[src v]]
                              (mapv 
                               (fn [[dst w]]
                                 [src dst w]) v)))
                           (into #{})
                           vec))
  (graph-add-vertex [this vertex]
    (assoc this vertex {}))
  (graph-add-edge [this [src dist w]]
    (assoc-in this [src dist] w))
  (graph-bfs [g start]
    (loop [final [start] q (reduce conj clojure.lang.PersistentQueue/EMPTY (-> g (get start) keys)) visited #{start}]
      (if (empty? q)
        final
        (let [current (-> q seq first)
              childrens (-> g (get current) keys)
              visited? (contains? visited current)]
          (recur (if-not visited?
                   (conj final current)
                   final)
                 (if-not visited?
                   (reduce conj (pop q) childrens)
                   (pop q))
                 (conj visited current)))))))

