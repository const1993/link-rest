package com.nhl.link.rest.runtime.protocol;

import com.nhl.link.rest.ResourceEntity;
import com.nhl.link.rest.it.fixture.cayenne.E2;
import com.nhl.link.rest.it.fixture.cayenne.auto._E2;
import com.nhl.link.rest.meta.LrAttribute;
import com.nhl.link.rest.meta.LrEntity;
import com.nhl.link.rest.protocol.Sort;
import com.nhl.link.rest.runtime.entity.SortMerger;
import com.nhl.link.rest.runtime.jackson.JacksonService;
import com.nhl.link.rest.runtime.path.PathDescriptorManager;
import com.nhl.link.rest.unit.TestWithCayenneMapping;
import org.apache.cayenne.query.Ordering;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// TODO: split merger test
public class SortParserTest extends TestWithCayenneMapping {

	private SortParser parser;
	private SortMerger constructor;
	private ResourceEntity<?> entity;

	@Before
	public void before() {
		JacksonService jacksonService = new JacksonService();
		this.parser = new SortParser(jacksonService);
		this.constructor = new SortMerger(new PathDescriptorManager());

		@SuppressWarnings("unchecked")
		LrEntity<E2> lre2 = mock(LrEntity.class);
		when(lre2.getType()).thenReturn(E2.class);
		when(lre2.getName()).thenReturn("E2");
		when(lre2.getAttribute("name")).thenReturn(mock(LrAttribute.class));
		when(lre2.getAttribute("address")).thenReturn(mock(LrAttribute.class));

		this.entity = new ResourceEntity<>(lre2);
	}

	@Test
	public void testProcess_Array() {

        Sort sort = parser.fromString("[{\"property\":\"name\"},{\"property\":\"address\"}]");
	    constructor.merge(entity, sort);

		assertEquals(2, entity.getOrderings().size());

		Iterator<Ordering> it = entity.getOrderings().iterator();
		Ordering o1 = it.next();
		Ordering o2 = it.next();

		assertEquals(_E2.NAME.getName(), o1.getSortSpecString());
		assertEquals(_E2.ADDRESS.getName(), o2.getSortSpecString());
	}

	@Test
	public void testProcess_Object() {

        Sort sort = parser.fromString("{\"property\":\"name\"}");
        constructor.merge(entity, sort);

		assertEquals(1, entity.getOrderings().size());

		Iterator<Ordering> it = entity.getOrderings().iterator();
		Ordering o1 = it.next();

		assertEquals(_E2.NAME.getName(), o1.getSortSpecString());
	}

	@Test
	public void testProcess_Simple() {

        Sort sort = parser.fromString("name");
        constructor.merge(entity, sort);

		assertEquals(1, entity.getOrderings().size());

		Iterator<Ordering> it = entity.getOrderings().iterator();
		Ordering o1 = it.next();

		assertEquals(_E2.NAME.getName(), o1.getSortSpecString());
	}

}
