package com.tigerbrokers.stock.openapi.client.struct.enums;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class EnumCoverageTest {

  @Test
  public void testAccumulateField() {
    Set<String> values = AccumulateField.getAllValues();
    Assert.assertTrue(values.size() > 10);
    Integer idx = AccumulateField.getIndexByValue("changeRate");
    Assert.assertEquals(Integer.valueOf(1), idx);
    Assert.assertNull(AccumulateField.getIndexByValue("nonexistent"));
    AccumulateField field = AccumulateField.getTypeByIndex(1);
    Assert.assertNotNull(field);
    Assert.assertNull(AccumulateField.getTypeByIndex(999));
    field.setValue("test");
    Assert.assertEquals("test", field.getValue());
    field.setIndex(100);
    Assert.assertEquals(Integer.valueOf(100), field.getIndex());
    field.setCombineSign("sign");
    Assert.assertEquals("sign", field.getCombineSign());
  }

  @Test
  public void testStockField_values() {
    for (StockField f : StockField.values()) {
      Assert.assertNotNull(f.toString());
    }
  }

  @Test
  public void testMultiTagField_values() {
    for (MultiTagField f : MultiTagField.values()) {
      Assert.assertNotNull(f.toString());
    }
  }

  @Test
  public void testFinancialField_values() {
    for (FinancialField f : FinancialField.values()) {
      Assert.assertNotNull(f.toString());
    }
  }

  @Test
  public void testAccumulatePeriod_values() {
    for (AccumulatePeriod p : AccumulatePeriod.values()) {
      Assert.assertNotNull(p.toString());
    }
  }

  @Test
  public void testTimeZoneId_values() {
    for (TimeZoneId t : TimeZoneId.values()) {
      Assert.assertNotNull(t.toString());
    }
  }

  @Test
  public void testHaltedStatus_values() {
    for (HaltedStatus s : HaltedStatus.values()) {
      Assert.assertNotNull(s.toString());
    }
  }

  @Test
  public void testStockRankingIndicator_values() {
    for (StockRankingIndicator s : StockRankingIndicator.values()) {
      Assert.assertNotNull(s.toString());
    }
  }

  @Test
  public void testOptionRankingIndicator_values() {
    for (OptionRankingIndicator s : OptionRankingIndicator.values()) {
      Assert.assertNotNull(s.toString());
    }
  }

  @Test
  public void testFieldBelongType_values() {
    for (FieldBelongType t : FieldBelongType.values()) {
      Assert.assertNotNull(t.toString());
    }
  }

  @Test
  public void testSortDir_values() {
    for (SortDir s : SortDir.values()) {
      Assert.assertNotNull(s.toString());
    }
  }

  @Test
  public void testPartCode_values() {
    for (PartCode p : PartCode.values()) {
      Assert.assertNotNull(p.toString());
    }
  }


  @Test
  public void testStockField_staticMethods() {
    if (StockField.values().length > 0) {
      StockField first = StockField.values()[0];
      String val = first.getValue() != null ? first.getValue() : "";
      Assert.assertNotNull(StockField.getTypeByValue(val));
      Assert.assertNull(StockField.getTypeByValue("nonexistent"));
      Integer idx = StockField.getIndexByValue(val);
      Assert.assertNotNull(StockField.getValueByIndex(idx));
      Assert.assertNull(StockField.getValueByIndex(99999));
      Assert.assertNull(StockField.getIndexByValue("nonexistent"));
      Assert.assertNotNull(StockField.getTypeByIndex(idx));
      Assert.assertNull(StockField.getTypeByIndex(99999));
      Assert.assertTrue(StockField.getAllValues().size() > 0);
    }
  }

  @Test
  public void testMultiTagField_staticMethods() {
    if (MultiTagField.values().length > 0) {
      MultiTagField first = MultiTagField.values()[0];
      String val = first.getValue() != null ? first.getValue() : "";
      Assert.assertNotNull(MultiTagField.getTypeByValue(val));
      Assert.assertNull(MultiTagField.getTypeByValue("nonexistent"));
      Integer idx = MultiTagField.getIndexByValue(val);
      Assert.assertNull(MultiTagField.getIndexByValue("nonexistent"));
      Assert.assertNotNull(MultiTagField.getTypeByIndex(idx));
      Assert.assertNull(MultiTagField.getTypeByIndex(99999));
      Assert.assertTrue(MultiTagField.getAllValues().size() > 0);
    }
  }

  @Test
  public void testFinancialField_staticMethods() {
    if (FinancialField.values().length > 0) {
      FinancialField first = FinancialField.values()[0];
      String val = first.getValue() != null ? first.getValue() : "";
      Assert.assertNotNull(FinancialField.getTypeByValue(val));
      Assert.assertNull(FinancialField.getTypeByValue("nonexistent"));
      Integer idx = FinancialField.getIndexByValue(val);
      Assert.assertNotNull(FinancialField.getValueByIndex(idx));
      Assert.assertNull(FinancialField.getValueByIndex(99999));
      Assert.assertNull(FinancialField.getIndexByValue("nonexistent"));
      Assert.assertNotNull(FinancialField.getTypeByIndex(idx));
      Assert.assertNull(FinancialField.getTypeByIndex(99999));
      Assert.assertTrue(FinancialField.getAllValues().size() > 0);
    }
  }
}
