// Задание 1. Реализовать очередь на массиве

// Инвариант, пред- и постусловия записываются в исходном коде в виде комментариев

    /* Инвариант очереди: элементы могут добавляться в конец (enqueue)
    и удаляться из начала (dequeue).
    В очереди элементы обрабатываются в порядке их добавления
    (FIFO - First In, First Out).*/

/* enqueue(element):
    Предусловие: очередь не заполнена (должно быть достаточно места для добавления элемента).
    Постусловие: элемент добавлен в конец очереди; размер очереди увеличен на 1.
   dequeue():
    Предусловие: очередь не пуста.
    Постусловие: первый элемент удален из очереди; размер очереди уменьшен на 1; возвращен удаленный элемент.
   element():
    Предусловие: очередь не пуста.
    Постусловие: возвращен первый элемент очереди, не удаляя его.
   size():
    Предусловие: нет.
    Постусловие: возвращен текущий размер очереди.
   isEmpty():
    Предусловие: нет.
    Постусловие: возвращено значение, указывающее, пуста ли очередь.
   clear():
    Предусловие: нет.
    Постусловие: все элементы удалены из очереди; размер очереди равен 0. */

public class Task1 {

    /* Реализуйте классы, представляющие циклическую очередь с
применением массива. */

    /* Класс ArrayQueueModule должен реализовывать один
    экземпляр очереди с использованием переменных класса. */
    public static class ArrayQueueModule {
        private static final int DEFAULT_CAPACITY = 10; // Начальная емкость
        private Object[] elements; // Массив для хранения элементов
        private int head; // Индекс первого элемента
        private int tail; // Индекс следующего элемента для добавления
        private int size; // Текущий размер очереди

        // Конструктор
        public ArrayQueueModule() {
            elements = new Object[DEFAULT_CAPACITY];
            head = 0;
            tail = 0;
            size = 0;
        }

        // Метод для добавления элемента в очередь
        public void enqueue(Object element) {
            // Предусловие: очередь не заполнена
            if (size == elements.length) {
                resize(); // увеличиваем емкость массива
            }
            elements[tail] = element; // добавляем элемента
            tail = (tail + 1) % elements.length; // осуществляем циклический переход
            size++; // увеличиваем размера
        }

        // Удаление и возвращение первого элемента
        public Object dequeue() {
            // Предусловие: очередь не пуста
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            Object result = elements[head]; // сохраняем первый элемент
            elements[head] = null; // удаляем ссылку на элемент
            head = (head + 1) % elements.length; // осуществляем циклический переход
            size--; // уменьшаем размер
            return result;
        }

        // Возвращение первого элемента без удаления
        public Object element() {
            // Предусловие: очередь не пуста
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            return elements[head];
        }

        // Возвращение текущего размера очереди
        public int size() {
            return size;
        }

        // Проверка на пустоту очереди
        public boolean isEmpty() {
            return size == 0;
        }

        // Очистка очереди
        public void clear() {
            while (!isEmpty()) { // пока очередь не пуста
                dequeue(); // реализуем метод удаления первого элемента
            }
        }

        // Увеличение емкости массива
        private void resize() {
            // Создаём новый массив в два раза больше текущего
            Object[] newElements = new Object[elements.length * 2];
            for (int i = 0; i < size; i++) {
                // Копирование элементов из старого массива в новый
                newElements[i] = elements[(head + i) % elements.length];
            }
            // Заменяем старый массив на новый
            elements = newElements;
            head = 0; // первый элемент - в начале
            tail = size; // указывает на следующую свободную позицию в очереди
        }
    }

    /* Класс ArrayQueueADT должен реализовывать очередь в виде
    абстрактного типа данных (с явной передачей ссылки на
    экземпляр очереди). */
    public static class ArrayQueueADT {
        private Object[] elements; // Массив для хранения элементов
        private int head; // Индекс первого элемента
        private int tail; // Индекс следующего элемента для добавления
        private int size; // Текущий размер очереди

        // Конструктор
        public ArrayQueueADT(int capacity) {
            elements = new Object[capacity];
            head = 0;
            tail = 0;
            size = 0;
        }

        // Метод для добавления элемента в очередь
        public void enqueue(ArrayQueueADT queue, Object element) {
            if (queue.size == queue.elements.length) {
                resize(queue);
            }
            queue.elements[queue.tail] = element;
            queue.tail = (queue.tail + 1) % queue.elements.length;
            queue.size++;
        }

        // Удаление и возвращение первого элемента
        public Object dequeue(ArrayQueueADT queue) {
            if (isEmpty(queue)) {
                throw new IllegalStateException("Очередь пуста");
            }
            Object result = queue.elements[queue.head];
            queue.elements[queue.head] = null;
            queue.head = (queue.head + 1) % queue.elements.length;
            queue.size--;
            return result;
        }

        // Возвращение первого элемента без удаления
        public Object element(ArrayQueueADT queue) {
            if (isEmpty(queue)) {
                throw new IllegalStateException("Очередь пуста");
            }
            return queue.elements[queue.head];
        }

        // Возвращение текущего размера очереди
        public int size(ArrayQueueADT queue) {
            return queue.size;
        }

        // Проверка на пустоту очереди
        public boolean isEmpty(ArrayQueueADT queue) {
            return queue.size == 0;
        }

        // Очистка очереди
        public void clear(ArrayQueueADT queue) {
            while (!isEmpty(queue)) {
                dequeue(queue);
            }
        }

        // Увеличение емкости массива
        private void resize(ArrayQueueADT queue) {
            Object[] newElements = new Object[queue.elements.length * 2];
            for (int i = 0; i < queue.size; i++) {
                newElements[i] = queue.elements[(queue.head + i) % queue.elements.length];
            }
            queue.elements = newElements;
            queue.head = 0;
            queue.tail = queue.size;
        }
    }

    /* Класс ArrayQueue должен реализовывать очередь в виде класса
    (с неявной передачей ссылки на экземпляр очереди) */
    public static class ArrayQueue {
        // Ссылка на экземпляр очереди в поле данных
        private ArrayQueueModule queueModule;

        // Конструктор
        public ArrayQueue() {
            queueModule = new ArrayQueueModule();
        }

        // Метод для добавления элемента в очередь
        public void enqueue(Object element) {
            queueModule.enqueue(element);
        }

        // Удаление и возвращение первого элемента
        public Object dequeue() {
            return queueModule.dequeue();
        }

        // Возвращение первого элемента без удаления
        public Object element() {
            return queueModule.element();
        }

        // Возвращение текущего размера очереди
        public int size() {
            return queueModule.size();
        }

        // Проверка на пустоту очереди
        public boolean isEmpty() {
            return queueModule.isEmpty();
        }

        // Очистка очереди
        public void clear() {
            queueModule.clear();
        }
    }

    public static void main(String[] args) {
        // Тестирование ArrayQueueModule
        System.out.println("Тестирование ArrayQueueModule");
        ArrayQueueModule queueModule = new ArrayQueueModule();
        queueModule.enqueue(1);
        queueModule.enqueue(2);
        queueModule.enqueue(3);
        System.out.println("Удалённый элемент: " + queueModule.dequeue()); // 1
        System.out.println("Первый элемент очереди: " + queueModule.element()); // 2
        System.out.println("Размер очереди: " + queueModule.size()); // 2
        System.out.println("Массив пуст? " + queueModule.isEmpty()); // false
        queueModule.clear();
        System.out.println("Массив пуст? " + queueModule.isEmpty()); // true

        // Тестирование ArrayQueueADT
        System.out.println("Тестирование ArrayQueueADT");
        ArrayQueueADT queueADT = new ArrayQueueADT(5);
        queueADT.enqueue(queueADT, 1);
        queueADT.enqueue(queueADT, 2);
        queueADT.enqueue(queueADT, 3);
        System.out.println("Удалённый элемент: " + queueADT.dequeue(queueADT)); // 1
        System.out.println("Первый элемент очереди: " + queueADT.element(queueADT)); // 2
        System.out.println("Размер очереди: " + queueADT.size(queueADT)); // 2
        System.out.println("Массив пуст? " + queueADT.isEmpty(queueADT)); // false
        queueADT.clear(queueADT);
        System.out.println("Массив пуст? " + queueADT.isEmpty(queueADT)); // true

        // Тестирование ArrayQueue
        System.out.println("Тестирование ArrayQueue");
        ArrayQueue arrayQueue = new ArrayQueue();
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        System.out.println("Удалённый элемент: " + arrayQueue.dequeue()); // 1
        System.out.println("Первый элемент очереди: " + arrayQueue.element()); // 2
        System.out.println("Размер очереди: " + arrayQueue.size()); // 2
        System.out.println("Массив пуст? " + arrayQueue.isEmpty()); // false
        arrayQueue.clear();
        System.out.println("Массив пуст? " + arrayQueue.isEmpty()); // true
    }
}