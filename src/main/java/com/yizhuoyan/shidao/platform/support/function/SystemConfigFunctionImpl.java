package com.yizhuoyan.shidao.platform.support.function;

import com.yizhuoyan.common.dao.support.SelectLikePo;
import com.yizhuoyan.common.util.KeyValueMap;
import com.yizhuoyan.common.util.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.platform.dao.SystemConfigDao;
import com.yizhuoyan.shidao.platform.po.SystemConfigPo;
import com.yizhuoyan.shidao.entity.SystemConfigEntity;
import com.yizhuoyan.shidao.platform.function.SystemConfigFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
import java.util.List;
import java.util.Objects;

import static com.yizhuoyan.common.util.AssertThrowUtil.*;
import static com.yizhuoyan.common.util.PlatformUtil.trim;
import static com.yizhuoyan.common.util.PlatformUtil.uuid12;
import static com.yizhuoyan.common.util.validatation.ParameterValidator.$;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
@Service
public class SystemConfigFunctionImpl implements SystemConfigFunction {
    @Autowired
    protected SystemConfigDao configDao;
    @Override
    public List<SystemConfigEntity> listSystemConfig(String key)
            throws Exception {
        key = trim(key);
        List<SystemConfigEntity> result = this.configDao.selectsByLike(
                SelectLikePo.of("name,value,remark",key)
                        .setOrderBy("name"));
        return result;
    }

    @Override
    public SystemConfigEntity checkSystemConfigDetail(String id) throws Exception {
        id = $("id", id);
        SystemConfigEntity item = configDao.select("id", id);
        assertNotNull("not-exist.id", item, id);
        return item;
    }

    @Override
    public SystemConfigEntity addSystemConfig(SystemConfigPo po)
            throws Exception {
        ParameterObjectValidator.throwIfFail(po, Default.class);
        // 配置命名不能重复
        String name = po.getName();
        assertFalse("already-exist.name", configDao.exist("name", name), name);

        SystemConfigEntity item = new SystemConfigEntity();
        item.setId(uuid12());
        item.setName(po.getName());
        item.setValue(po.getValue());
        item.setRemark(trim(po.getRemark()));
        int status = po.getStatus();
        item.setStatus(status);
        this.configDao.insert(item);
        return item;
    }


    @Override
    public SystemConfigEntity modifySystemConfig(String id, SystemConfigPo po) throws Exception {
        id = $("id", id);
        SystemConfigEntity old = configDao.select("id", id);
        assertNotNull("not-exist.id", old, id);
        ParameterObjectValidator.throwIfFail(po);
        KeyValueMap needUpdate = new KeyValueMap(4);

        String newName = po.getName();
        if (!Objects.equals(newName, old.getName())) {
            assertFalse("dataExist", configDao.exist("name", newName));
            needUpdate.put("name", newName);
            old.setName(newName);
        }

        String newValue = po.getValue();
        if (!Objects.equals(newValue, old.getValue())) {
            needUpdate.put("value", newValue);
            old.setValue(newValue);
        }

        String newRemark = po.getRemark();
        if (!Objects.equals(newRemark, old.getRemark())) {
            needUpdate.put("remark", newRemark);
            old.setRemark(newRemark);
        }
        int newStatus = po.getStatus();
        if (newStatus == old.getStatus()) {
            needUpdate.put("status", newStatus);
            old.setStatus(newStatus);
        }
        configDao.update(id, needUpdate);
        return old;
    }


}
