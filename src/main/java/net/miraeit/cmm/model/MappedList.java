package net.miraeit.cmm.model;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappedList<K, T> {
	@FunctionalInterface
	public interface KeyMapper<K, T> {
		K getKey(T item);
	}

	private KeyMapper<K, T> keyMapper;

	private List<T> collection = new ArrayList<>();
	private Map<K, List<T>> map = new HashMap<>();

	public MappedList(KeyMapper<K, T> keyMapper) {
		this.keyMapper = keyMapper;
	}

	public MappedList(Collection<T> list, KeyMapper<K, T> keyMapper) {
		this.keyMapper = keyMapper;
		list.forEach(this::add);
	}

	public T get(K key) {
		List<T> valueList = map.get(key);
		if(CollectionUtils.isEmpty(valueList)) return null;
		return valueList.get(0);
	}

	public List<T> getList(K key) {
		List<T> valueList = map.get(key);
		if(valueList == null) return new ArrayList<>();
		return valueList;
	}

	public void add(T value) {
		this.collection.add(value);
		K key = keyMapper.getKey(value);
		List<T> valueList = map.computeIfAbsent(key, k -> new ArrayList<>());
//		아래의 내용이 위의 한줄로...
//		List<T> valueList = map.get(key);
//		if(valueList==null) {
//			valueList = new ArrayList<>();
//			map.put(key, valueList);
//		}
		valueList.add(value);
	}

	/**
	 * keyMapper로 얻을 수 없는 키로 넣어야 한다면...
	 * @param key
	 * @param value
	 */
	public void add(K key, T value) {
		this.collection.add(value);
		List<T> valueList = map.computeIfAbsent(key, k -> new ArrayList<>());
		valueList.add(value);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public List<T> getCollection() {
		return this.collection;
	}
}
