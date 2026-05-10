package com.wtt.demo.service;

import com.wtt.demo.mapper.EquipmentMapper;
import com.wtt.demo.pojo.Equipment;
import com.wtt.demo.service.Impl.EquipmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private EquipmentMapper equipmentMapper;

    @InjectMocks
    private EquipmentServiceImpl equipmentService;

    private Equipment testEquipment;

    @BeforeEach
    void setUp() {
        testEquipment = new Equipment();
        testEquipment.setPlayerName("张三");
        testEquipment.setBlade("红双喜968");
        testEquipment.setForehandRubber("狂飙3");
        testEquipment.setBackhandRubber("焦点3");
    }

    @Test
    void findAll_ShouldReturnAllEquipment() {
        List<Equipment> equipmentList = Arrays.asList(testEquipment);
        when(equipmentMapper.findAll()).thenReturn(equipmentList);

        List<Equipment> result = equipmentService.findAll();

        assertEquals(1, result.size());
        assertEquals("张三", result.get(0).getPlayerName());
        verify(equipmentMapper, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoEquipment() {
        when(equipmentMapper.findAll()).thenReturn(Arrays.asList());

        List<Equipment> result = equipmentService.findAll();

        assertTrue(result.isEmpty());
        verify(equipmentMapper, times(1)).findAll();
    }

    @Test
    void selectByPlayerName_ShouldReturnEquipment_WhenExists() {
        when(equipmentMapper.selectByPlayerName("张三")).thenReturn(testEquipment);

        Equipment result = equipmentService.selectByPlayerName("张三");

        assertNotNull(result);
        assertEquals("红双喜968", result.getBlade());
        assertEquals("狂飙3", result.getForehandRubber());
        assertEquals("焦点3", result.getBackhandRubber());
        verify(equipmentMapper, times(1)).selectByPlayerName("张三");
    }

    @Test
    void selectByPlayerName_ShouldReturnNull_WhenNotExists() {
        when(equipmentMapper.selectByPlayerName("不存在")).thenReturn(null);

        Equipment result = equipmentService.selectByPlayerName("不存在");

        assertNull(result);
        verify(equipmentMapper, times(1)).selectByPlayerName("不存在");
    }

    @Test
    void selectTotalCount_ShouldReturnCount() {
        when(equipmentMapper.selectTotalCount()).thenReturn(5);

        Integer result = equipmentService.selectTotalCount();

        assertEquals(5, result);
        verify(equipmentMapper, times(1)).selectTotalCount();
    }

    @Test
    void insert_ShouldReturn1_WhenSuccess() {
        Equipment newEquipment = new Equipment();
        newEquipment.setPlayerName("李四");
        newEquipment.setBlade("蝴蝶Vps");
        newEquipment.setForehandRubber("T05");
        newEquipment.setBackhandRubber("T64");

        when(equipmentMapper.insert(any(Equipment.class))).thenReturn(1);

        int result = equipmentService.insert(newEquipment);

        assertEquals(1, result);
        verify(equipmentMapper, times(1)).insert(newEquipment);
    }

    @Test
    void insert_ShouldReturn0_WhenFails() {
        Equipment newEquipment = new Equipment();
        newEquipment.setPlayerName("王五");

        when(equipmentMapper.insert(newEquipment)).thenReturn(0);

        int result = equipmentService.insert(newEquipment);

        assertEquals(0, result);
        verify(equipmentMapper, times(1)).insert(newEquipment);
    }

    @Test
    void deleteByPlayerName_ShouldReturn1_WhenSuccess() {
        when(equipmentMapper.deleteByPlayerName("张三")).thenReturn(1);

        int result = equipmentService.deleteByPlayerName("张三");

        assertEquals(1, result);
        verify(equipmentMapper, times(1)).deleteByPlayerName("张三");
    }

    @Test
    void deleteByPlayerName_ShouldReturn0_WhenNotExists() {
        when(equipmentMapper.deleteByPlayerName("不存在")).thenReturn(0);

        int result = equipmentService.deleteByPlayerName("不存在");

        assertEquals(0, result);
        verify(equipmentMapper, times(1)).deleteByPlayerName("不存在");
    }

    @Test
    void update_ShouldReturn1_WhenSuccess() {
        Equipment updateEquipment = new Equipment();
        updateEquipment.setPlayerName("张三");
        updateEquipment.setBlade("红双喜W968");
        updateEquipment.setForehandRubber("蓝海绵狂飙3");
        updateEquipment.setBackhandRubber("尤拉雷霆");

        when(equipmentMapper.update(any(Equipment.class))).thenReturn(1);

        int result = equipmentService.update(updateEquipment);

        assertEquals(1, result);
        verify(equipmentMapper, times(1)).update(updateEquipment);
    }

    @Test
    void update_ShouldReturn0_WhenPlayerNotExists() {
        Equipment updateEquipment = new Equipment();
        updateEquipment.setPlayerName("不存在");
        updateEquipment.setBlade("test");

        when(equipmentMapper.update(updateEquipment)).thenReturn(0);

        int result = equipmentService.update(updateEquipment);

        assertEquals(0, result);
        verify(equipmentMapper, times(1)).update(updateEquipment);
    }

    @Test
    void selectByPlayerName_ShouldReturnFullEquipmentDetails() {
        Equipment fullEquipment = new Equipment();
        fullEquipment.setPlayerName("测试球员");
        fullEquipment.setBlade("品牌型号X");
        fullEquipment.setForehandRubber("正手胶皮Y");
        fullEquipment.setBackhandRubber("反手胶皮Z");

        when(equipmentMapper.selectByPlayerName("测试球员")).thenReturn(fullEquipment);

        Equipment result = equipmentService.selectByPlayerName("测试球员");

        assertNotNull(result);
        assertEquals("品牌型号X", result.getBlade());
        assertEquals("正手胶皮Y", result.getForehandRubber());
        assertEquals("反手胶皮Z", result.getBackhandRubber());
    }
}