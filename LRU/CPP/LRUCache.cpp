
class LRUCache {
private:
    int time = 0;
    unordered_map<int, int> lru;
    int size = 0;
    int c;
    unordered_map<int, int> used;

public:
    LRUCache(int capacity) {
        c = capacity;
    }

    int get(int key) {
        if (lru.find(key) != lru.end()) {
            time++;
            used[key] = time;

            return lru[key];
        }

        return -1;
    }

    void put(int key, int value) {

        // Existing key
        if (lru.find(key) != lru.end()) {
            lru[key] = value;
            time++;
            used[key] = time;
            return;
        }

        if (size < c) {
            time++;
            used[key] = time;
            lru[key] = value;
            size = lru.size();

        } else {
            int min = -1;
            int f = INT_MAX;

            for (auto k : used) {
                if (k.second < f) {
                    min = k.first;
                    f = k.second;
                }
            }

            lru.erase(min);
            used.erase(min);

            time++;
            used[key] = time;
            lru[key] = value;

            size = lru.size();
        }
    }
};
