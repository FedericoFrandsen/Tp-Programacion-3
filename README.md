# Tp-Programacion-3

1. Introducción
El Knight’s Tour Problem es un problema clásico de teoría de grafos y algoritmos: encontrar un recorrido de un caballo de ajedrez en un tablero de n x  n que pase por todas las casillas exactamente una vez.
 Este problema se relaciona con conceptos de backtracking, heurísticas y programación dinámica, permitiendo analizar diferentes enfoques algorítmicos y comparar su rendimiento.
En este trabajo se desarrollan tres variantes:
Backtracking tradicional
Heurística greedy (Regla de Warnsdorff)
Programación dinámica (maximización de puntajes en k movimientos)
Finalmente, se analizan y comparan sus complejidades temporales y espaciales.

2. Backtracking
El caballo intenta todos los caminos posibles recursivamente. Si en algún momento queda atrapado, retrocede (backtracking) y prueba otra ruta.
Idea básica:
Representar el tablero como una matriz.
En cada paso, intentar todos los movimientos válidos del caballo.
Si se logra visitar n^2 casillas, se encontró una solución.
Ejemplo ejecución (N=5):
 (ejemplo de salida del programa, agregar tu captura de pantalla del tablero con números indicados en orden de visita)

3. Heurística Greedy (Regla de Warnsdorff)
La regla de Warnsdorff propone un enfoque más eficiente:
“El caballo debe moverse siempre a la celda que tenga el menor número de movimientos futuros disponibles.”
Esto evita rápidamente “atascarse” en regiones del tablero.
Ventajas:
Alta probabilidad de encontrar un tour completo.
Complejidad casi lineal respecto al número de casillas.
Ejemplo ejecución (N=6):
 (insertar captura mostrando la numeración del recorrido obtenido por el método greedy)

4.  Programación Dinámica (variación con puntajes y k movimientos)
En esta variante, cada casilla del tablero tiene un puntaje asociado. El objetivo no es visitar todas las casillas, sino maximizar la suma de puntos en exactamente k movimientos.
Definición de estado:

dp[x][y][m] = máximo puntaje alcanzable partiendo de la casilla  (x,y)  con m movimientos restantes.
Recurrencia:
dp[x][y][m] = puntos(x,y) + max( dp[x+dx][y+dy][m-1] ) 
              considerando todos los movimientos posibles (dx, dy) del caballo

Se implementó con memoización (HashMap en Java).
Ejemplo de ejecución con tablero de 6x6 y k=4:
 “Máxima suma en 4 movimientos: 30” (ejemplo, depende de la matriz de puntajes usada).


5. Complejidad de los algoritmos

Método               Complejidad Temporal            Complejidad Espacial           Observaciones
----------------------------------------------------------------------------------------------------------
Backtracking         Exponencial O(8^(n^2))          O(n^2) (recursión + tablero)   Inviable salvo en tableros chicos (N ≤ 6).
Warnsdorff (Greedy)  O(n^2 * 8) ≈ O(n^2)             O(n^2)                         Muy eficiente, funciona bien en la práctica.
DP con puntajes      O(k * n^2 * 8)                  O(k * n^2)                     Escalable para valores moderados de k.




6. Reflexión
El backtracking es útil como aproximación teórica para entender el problema, pero resulta ineficiente para tableros grandes.
La heurística greedy de Warnsdorff es la más adecuada para resolver el Knight’s Tour clásico: encuentra soluciones completas de forma rápida incluso en tableros grandes (ej. 8x8).
La programación dinámica es la técnica adecuada cuando el problema cambia de objetivo (maximizar puntajes en movimientos limitados). Permite optimizaciones con memoria razonable si k es acotado.
En conclusión, no existe una sola “mejor técnica”: depende de la variante del problema. Para el tour clásico, Warnsdorff es claramente superior; para el problema de maximizar puntajes, la DP se vuelve necesaria.
