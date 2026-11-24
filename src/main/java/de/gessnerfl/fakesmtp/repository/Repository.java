package de.gessnerfl.fakesmtp.repository;

import de.gessnerfl.fakesmtp.model.IdHolder;
import de.gessnerfl.fakesmtp.model.query.SearchSpecification;
import org.springframework.data.domain.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static de.gessnerfl.fakesmtp.model.query.PropertyUtils.getProperty;
import static java.util.Collections.synchronizedMap;

public abstract class Repository<T extends IdHolder<Long>> {
	protected Map<Long,T> entities = synchronizedMap(new LinkedHashMap<>());

	private AtomicLong nextId = new AtomicLong(1L);

	public T save(T entity) {
		long id = nextId.getAndIncrement();
		entity.setId(id);
		entities.put(id, entity);
		return entity;
	}

	public Optional<T> findById(long id) {
		return Optional.ofNullable(entities.get(id));
	}

	protected synchronized Collection<T> getEntities() {
		return entities.values();
	}

	public void deleteById(long id) {
		entities.remove(id);
	}

	public void flush() {
	}

	public void deleteAllInBatch() {
		deleteAll();
	}

	public void deleteAll() {
		entities.clear();
	}

	public Page<T> findAll(SearchSpecification<T> specification, Pageable pageable) {
		var pred = specification == null ? null : specification.toPredicate();
		var matches = pred==null
				? entities.values()
				: entities.values().stream().filter(specification.toPredicate()).toList();
		long total = matches.size();
		var items = matches.stream()
				.sorted(getComparator(pageable.getSort()))
				.skip(pageable.getOffset())
				.limit(pageable.getPageSize())
				.toList();
		return new PageImpl<>(items, pageable, total);
	}

	public Page<T> findAll(Pageable pageable) {
		return findAll(null, pageable);
	}

	public List<T> findAll() {
		return List.copyOf(getEntities());
	}

	public List<T> findAll(Sort sort) {
		return getEntities().stream().sorted(this::defaultOrder).toList();
	}

	protected abstract int defaultOrder(T o1, T o2);

	Comparator<T> getComparator(Sort sort) {
		if (sort.isSorted()) {
			return (o1,o2) -> {
				for(Sort.Order order: sort) {
					int cmp = getProperty(o1,order.getProperty(), Comparable.class).compareTo(getProperty(o2, order.getProperty()));
					if (cmp!=0) {
						return order.isAscending() ? cmp : -cmp;
					}
				}
				return 0;
			};
		} else {
			return this::defaultOrder;
		}
	}
}
