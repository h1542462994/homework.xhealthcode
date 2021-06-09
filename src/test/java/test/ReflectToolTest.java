package test;

import ext.util.ReflectTool;
import models.College;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import test.mock.MockBean;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReflectToolTest {

    @Order(0)
    @Test
    public void testGetString() throws NoSuchFieldException, IllegalAccessException {
        College college = new College();
        college.setName("hello");
        Field field = college.getClass().getDeclaredField("name");
        String value = (String) ReflectTool.getValue(college, field);
        assertEquals("hello", value);
    }

    @Order(1)
    @Test
    public void testGetBoolean() throws NoSuchFieldException, IllegalAccessException {
        MockBean mockBean = new MockBean();
        mockBean.setValue(true);
        Field field = mockBean.getClass().getDeclaredField("value");
        boolean value = (boolean) ReflectTool.getValue(mockBean, field);
        assertTrue(value);
    }

    @Order(2)
    @Test
    public void testSetString() throws NoSuchFieldException, IllegalAccessException {
        College college = new College();
        assertNull(college.getName());
        Field field = college.getClass().getDeclaredField("name");
        ReflectTool.setValue(college, field, "hello");
        assertEquals("hello", college.getName());
    }

    @Order(3)
    @Test
    public void testSetBoolean() throws NoSuchFieldException, IllegalAccessException {
        MockBean mockBean = new MockBean();
        mockBean.setValue(false);
        assertFalse(mockBean.isValue());
        Field field = mockBean.getClass().getDeclaredField("value");
        ReflectTool.setValue(mockBean, field, true);
        assertTrue(mockBean.isValue());
    }

    /**
     * test field has no annotation @Rename
     */
    @Order(4)
    @Test
    public void testFieldOfRename1() throws NoSuchFieldException {
        College college = new College();
        Field field1 = college.getClass().getDeclaredField("name");
        Field field2 = ReflectTool.fieldOfRename(college.getClass(), "name");
        assertEquals(field1, field2);
    }

    /**
     * test field has annotation @Rename
     */
    @Order(5)
    @Test
    public void testFieldOfRename2() throws NoSuchFieldException {
        MockBean mockBean = new MockBean();
        Field field1 = mockBean.getClass().getDeclaredField("text");
        Field field2 = ReflectTool.fieldOfRename(mockBean.getClass(), "text2");
        assertEquals(field1, field2);
    }

    @Order(6)
    @Test
    public void testRenameOfField1() throws NoSuchFieldException {
        College college = new College();
        Field field = college.getClass().getDeclaredField("name");
        String fieldName = ReflectTool.renameOfField(field);
        assertEquals("name", fieldName);
    }

    @Order(7)
    @Test
    public void testRenameOfField2() throws NoSuchFieldException {
        MockBean mockBean = new MockBean();
        Field field = mockBean.getClass().getDeclaredField("text");
        String fieldName = ReflectTool.renameOfField(field);
        assertEquals("text2", fieldName);
    }

    @Order(8)
    @Test
    public void testEntityName1() {
        College college = new College();
        String entityName = ReflectTool.getEntityName(college.getClass());
        assertEquals("college", entityName);
    }

    @Order(9)
    @Test
    public void testEntityName2() {
        MockBean mockBean = new MockBean();
        String entityName = ReflectTool.getEntityName(mockBean.getClass());
        assertEquals("MockBean", entityName);
    }

    @Order(10)
    @Test
    public void testPrimary() throws NoSuchFieldException {
        College college = new College();
        Field field1 = college.getClass().getDeclaredField("collegeId");
        Field field2 = ReflectTool.getPrimary(college.getClass());

        assertEquals(field1, field2);
    }

    @Order(11)
    @Test
    public void testPrimaryRename1() throws NoSuchFieldException {
        College college = new College();
        assertEquals("collegeId", ReflectTool.getPrimaryRenameName(college.getClass()));
    }

    @Order(12)
    @Test
    public void testPrimaryRename2() throws NoSuchFieldException {
        MockBean mockBean = new MockBean();
        assertEquals("text2", ReflectTool.getPrimaryRenameName(mockBean.getClass()));
    }

    @Order(13)
    @Test
    public void testPrimaryValue1() throws NoSuchFieldException, IllegalAccessException {
        College college = new College();
        college.setCollegeId(10);
        assertEquals(10L, ReflectTool.getPrimaryValue(college));
    }

    @Order(14)
    @Test
    public void testPrimaryValue2() throws NoSuchFieldException, IllegalAccessException {
        MockBean mockBean = new MockBean();
        mockBean.setText("test2");
        assertEquals("test2", ReflectTool.getPrimaryValue(mockBean));
    }
}
