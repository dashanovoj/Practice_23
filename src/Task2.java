// Задание 2. Очередь на связанном списке

public class Task2 {

    // Определите интерфейс очереди Queue
    public interface Queue {
        void enqueue(Object element); // добавление элемента в конец очереди
        Object dequeue(); // удаление и возвращение первого элемента очереди
        Object element(); // возвращение первого элемента без удаления
        int size(); // возвращение текущего размера очереди
        boolean isEmpty(); // проверка на пустоту очереди
        void clear(); // очистка очереди
    }

    // Выделите общие части классов LinkedQueue и ArrayQueue в базовый класс AbstractQueue
    public abstract class AbstractQueue implements Queue {
        protected int size; // Текущий размер очереди
        @Override
        public int size() {
            return size; // Возвращаем текущий размер очереди
        }
        @Override
        public boolean isEmpty() {
            return size == 0; // Проверяем, пуста ли очередь
        }
    }

    // Реализуйте класс LinkedQueue — очередь на связном списке.
    public class LinkedQueue extends AbstractQueue {
        private Node head; // Указатель на первый элемент
        private Node tail; // Указатель на последний элемент

        // Вложенный класс для реализации узла списка
        private static class Node {
            // Поля данных
            Object value; // Значение узла
            Node next; // Указатель на следующий узел

            // Конструктор
            Node(Object value) {
                this.value = value;
            }
        }

        // Конструктор
        public LinkedQueue() {
            head = null; // Изначально очередь пуста
            tail = null; // Изначально очередь пуста
        }

        // Метод для добавления элемента в очередь
        @Override
        public void enqueue(Object element) {
            Node newNode = new Node(element); // Создаем новый узел
            if (isEmpty()) {
                head = newNode; // Если очередь пуста, новый узел становится головой
            } else {
                tail.next = newNode; // Присоединяем новый узел к концу очереди
            }
            tail = newNode; // Обновляем указатель tail
            size++; // Увеличиваем размер очереди
        }

        // Метод для удаления и возвращения первого элемента
        @Override
        public Object dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            Object result = head.value; // Сохраняем значение первого элемента
            head = head.next; // Сдвигаем указатель head на следующий элемент
            if (head == null) {
                tail = null; // Если очередь стала пустой, обновляем указатель tail
            }
            size--; // Уменьшаем размер очереди
            return result; // Возвращаем значение удаленного элемента
        }

        // Метод для возвращения первого элемента без удаления
        @Override
        public Object element() {
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            return head.value; // Возвращаем значение первого элемента
        }

        // Метод для очистки очереди
        @Override
        public void clear() {
            head = null; // Удаляем все элементы, устанавливая head в null
            tail = null; // Устанавливаем tail в null
            size = 0; // Сбрасываем размер очереди
        }
    }

    // Реализуйте класс ArrayQueue — очередь на массиве.
    public class ArrayQueue extends AbstractQueue {
        private Object[] elements; // Массив для хранения элементов
        private int head; // Индекс первого элемента
        private int tail; // Индекс следующего элемента для добавления

        // Конструктор
        public ArrayQueue() {
            elements = new Object[10]; // Начальная емкость
            head = 0;
            tail = 0;
            size = 0;
        }

        // Метод для добавления элемента в очередь
        @Override
        public void enqueue(Object element) {
            if (size == elements.length) {
                resize(); // Увеличиваем емкость массива, если нужно
            }
            elements[tail] = element;
            tail = (tail + 1) % elements.length;
            size++;
        }

        // Метод для удаления и возвращения первого элемента
        @Override
        public Object dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            Object result = elements[head];
            elements[head] = null;
            head = (head + 1) % elements.length;
            size--;
            return result;
        }

        // Метод для возвращения первого элемента без удаления
        @Override
        public Object element() {
            if (isEmpty()) {
                throw new IllegalStateException("Очередь пуста");
            }
            return elements[head];
        }

        // Метод для очистки очереди
        @Override
        public void clear() {
            while (!isEmpty()) {
                dequeue();
            }
        }

        // Метод для увеличения емкости массива
        private void resize() {
            Object[] newElements = new Object[elements.length * 2];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[(head + i) % elements.length];
            }
            elements = newElements;
            head = 0;
            tail = size;
        }
    }
}
