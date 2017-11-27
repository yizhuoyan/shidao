package com.yizhuoyan.shidao.platform.support.function;

import com.yizhuoyan.shidao.common.dao.support.SelectLikePo;
import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.common.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.common.validatation.validategroup.Mod;
import com.yizhuoyan.shidao.platform.po.SystemConfigPo;
import com.yizhuoyan.shidao.platform.entity.SystemConfigModel;
import com.yizhuoyan.shidao.platform.function.SystemConfigFunction;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
import java.util.List;
import java.util.Objects;

import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.*;
import static com.yizhuoyan.shidao.common.util.AssertThrowUtil.assertNotNull;
import static com.yizhuoyan.shidao.common.validatation.ParameterValidator.$;
import static com.yizhuoyan.shidao.common.util.PlatformUtil.*;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
@Service
public class SystemConfigFunctionImpl extends AbstractFunctionSupport implements SystemConfigFunction {

    @Override
    public List<SystemConfigModel> listSystemConfig(String key)
            throws Exception {
        key = trim(key);
        List<SystemConfigModel> result = this.configDao.selectsByLike(
                SelectLikePo.of("name,value,remark",key)
                        .setOrderBy("name"));
        return result;
    }

    @Override
    public SystemConfigModel checkSystemConfigDetail(String id) throws Exception {
        id = $("id", id);
        SystemConfigModel item = configDao.select("id", id);
        assertNotNull("not-exist.id", item, id);
        return item;
    }

    @Override
    public SystemConfigModel addSystemConfig(SystemConfigPo po)
            throws Exception {
        ParameterObjectValidator.throwIfFail(po, Default.class);
        // 配置命名不能重复
        String name = po.getName();
        assertFalse("already-exist.name", configDao.exist("name", name), name);

        SystemConfigModel item = new SystemConfigModel();
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
    public SystemConfigModel modifySystemConfig(String id, SystemConfigPo po) throws Exception {
        id = $("id", id);
        SystemConfigModel old = configDao.select("id", id);
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
