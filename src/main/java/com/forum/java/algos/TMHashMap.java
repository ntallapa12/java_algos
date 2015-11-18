package com.forum.java.algos;


/**
 * This is an attempt to come up with my own hash data structure. This
 * illustration does not cover all the corner cases of actual hashmap but it
 * gives a good understanding on how we can come up with our own hash
 * implementations
 * 
 * @author ntallapa
 *
 */
public class TMHashMap {
	// for simplicity size is taken as 2^4
	private static final int SIZE = 16;
	private Entry table[] = new Entry[SIZE];

	/**
	 * User defined map data structure with key and value.
	 * 
	 * This is also used as linked list in case multiple key-value pairs lead to
	 * the same bucket with same hashcodes and different keys (collisions) using
	 * the pointer 'next'.
	 *
	 * @author ntallapa
	 */
	class Entry {
		Employee key;
		String value;
		Entry next;

		Entry(Employee k, String v) {
			key = k;
			value = v;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Employee getKey() {
			return key;
		}
	}

	/**
	 * * REFERENCE: JAVA SOURCE CODE *
	 * 
	 * Applies a supplemental hash function to a given hashCode, which defends
	 * against poor quality hash functions. This is critical because HashMap
	 * uses power-of-two length hash tables, that otherwise encounter collisions
	 * for hashCodes that do not differ in lower bits. Note: Null keys always
	 * map to hash 0, thus index 0.
	 */
	private int getSupplementalHash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	/**
	 * It makes sure the bucket number falls within the size of the hashmap
	 * 
	 * @param hash
	 * @return returns index for hashcode hash
	 */
	private int getBucketNumber(int hash) {
		return hash & (SIZE - 1);
	}

	/**
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for the key, the old value is
	 * replaced.
	 */
	public void put(Employee key, String value) {
		// get the hashcode and regenerate it to be optimum
		int userHash = key.hashCode();
		int hash = getSupplementalHash(userHash);

		// compute the bucket number (0-15 based on our default size)
		// this always results in a number between 0-15
		int bucket = getBucketNumber(hash);
		Entry existingElement = table[bucket];

		for (; existingElement != null; existingElement = existingElement.next) {

			if (existingElement.key.equals(key)) {
				System.out
						.println("duplicate key value pair, replacing existing key "
								+ key + ", with value " + value);
				existingElement.value = value;
				return;
			} else {
				System.out.println("Collision detected for key " + key
						+ ", adding element to the existing bucket");

			}
		}

		//
		System.out.println("PUT adding key:" + key + ", value:" + value
				+ " to the list");
		Entry entryInOldBucket = new Entry(key, value);
		entryInOldBucket.next = table[bucket];
		table[bucket] = entryInOldBucket;
	}

	/**
	 * Returns the entry associated with the specified key in the HashMap.
	 * Returns null if the HashMap contains no mapping for the key.
	 */
	public Entry get(Employee key) {
		// get the hashcode and regenerate it to be optimum
		int hash = getSupplementalHash(key.hashCode());

		// compute the bucket number (0-15 based on our default size)
		// this always results in a number between 0-15
		int bucket = getBucketNumber(hash);

		// get the element at the above bucket if it exists
		Entry existingElement = table[bucket];

		// if bucket is found then traverse through the linked list and
		// see if element is present
		while (existingElement != null) {
			System.out
					.println("Traversing the list inside the bucket for the key "
							+ existingElement.getKey());
			if (existingElement.key.equals(key)) {
				return existingElement;
			}
			existingElement = existingElement.next;
		}

		// if nothing is found then return null
		return null;
	}

	// for testing our own map
	public static void main(String[] args) {
		TMHashMap tmhm = new TMHashMap();

		System.out.println("============== Adding Element ===================");
		Employee e1 = new Employee(100, "Niranjan");
		tmhm.put(e1, "dept1");

		// duplicate
		System.out
				.println("============== Adding Duplicate ===================");
		Employee e1_dup = new Employee(100, "Niranjan");
		tmhm.put(e1_dup, "dept12");
		// the above value "dept12" should replace the old value "dept1"
		Entry e = tmhm.get(e1_dup);
		System.out.println("GET element - " + e.getKey() + "::" + e.getValue());

		System.out
				.println("============== Adding Elements ===================");
		Employee e2 = new Employee(102, "Sravan");
		tmhm.put(e2, "dept3");

		Employee e3 = new Employee(104, "Ananth");
		tmhm.put(e3, "dept2");

		Employee e4 = new Employee(106, "Rakesh");
		tmhm.put(e4, "dept5");

		Employee e5 = new Employee(108, "Shashi");
		tmhm.put(e5, "dept2");

		// collision with e2
		System.out
				.println("============== Adding Collisions ===================");
		Employee e2_collision = new Employee(112, "Chandu");
		tmhm.put(e2_collision, "dept16");
		e = tmhm.get(e2_collision);
		System.out.println("GET element - " + e.getKey() + "::" + e.getValue());

		// collision with e3
		Employee e3_collision = new Employee(114, "Santhosh");
		tmhm.put(e3_collision, "dept9");
		e = tmhm.get(e3_collision);
		System.out.println("GET element - " + e.getKey() + "::" + e.getValue());

		System.out
				.println("============== Adding Duplicate in Collision ===================");
		Employee e3_collision_dupe = new Employee(124, "Santhosh");
		tmhm.put(e3_collision_dupe, "dept91");
		e = tmhm.get(e3_collision_dupe);
		System.out.println("GET element - " + e.getKey() + "::" + e.getValue());

	}

	static class Employee {
		private Integer id;
		private String name;

		Employee(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int hashCode() {
			// this ensures all hashcodes are between 0 and 15
			return id % 10;
		}

		@Override
		public boolean equals(Object obj) {
			Employee otherEmp = (Employee) obj;
			return this.name.equals(otherEmp.name);
		}

		@Override
		public String toString() {
			return this.id + "-" + name;
		}
	}
}