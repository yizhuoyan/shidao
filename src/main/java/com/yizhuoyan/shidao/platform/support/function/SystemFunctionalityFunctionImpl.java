package com.yizhuoyan.shidao.platform.support.function;

import com.yizhuoyan.shidao.common.dao.support.SelectLikePo;
import com.yizhuoyan.shidao.common.util.KeyValueMap;
import com.yizhuoyan.shidao.common.validatation.ParameterObjectValidator;
import com.yizhuoyan.shidao.common.validatation.validategroup.Mod;
import com.yizhuoyan.shidao.platform.po.SystemFunctionalityPo;
import com.yizhuoyan.shidao.platform.entity.SystemFunctionalityModel;
import com.yizhuoyan.shidao.platform.entity.SystemRoleModel;
import com.yizhuoyan.shidao.platform.function.SystemFuncationalityFunction;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
import java.util.Collections;
import java.util.Date;
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
public class SystemFunctionalityFunctionImpl extends AbstractFunctionSupport implements SystemFuncationalityFunction
{
    /**
     * 验证功能代号的正确性
     * @param code
     * @param parent
     * @return
     */
    private String validateFunctionalityCode(String code,SystemFunctionalityModel parent){
        code=$("code",code);
        if(code.charAt(0)!='/'){
            code="/"+code;
        }
        //取出code中的父代号
        String inputParentCode=code.substring(0,code.lastIndexOf('/'));
        //取出子代号
        String inputChildCode=code.substring(inputParentCode.length()+1);
        assertFalse("must-less-than.code",inputChildCode.length()>4,4);
        String parentCode="";
        if(parent!=null){
            parentCode=parent.getCode();
        }
        assertEquals("functionality-must-match-parentcode.code",parentCode,inputParentCode,code);
        return code;
    }
    @Override
    public List<SystemRoleModel> listRoleOfSystemFunctionality(String functionalityId)
            throws Exception{
        functionalityId=$("functionalityId",functionalityId);
        List<SystemRoleModel> list = this.roleDao.selectByFunctionalityId(functionalityId);
        return list;
    }
    @Override
    public SystemFunctionalityModel addSystemFunctionality(SystemFunctionalityPo po) throws Exception{

        ParameterObjectValidator.throwIfFail(po);

        String parentId = trim(po.getParentId());

        // 1父模块
        SystemFunctionalityModel parentFunctionality = null;
        if(parentId!=null){
            parentFunctionality = this.functionalityDao.select("id",parentId);
            assertNotNull("not-exist.parentId", parentFunctionality,parentId);
        }

        // 2代号
        String code = po.getCode();
        //验证code的格式正确性
        code=validateFunctionalityCode(code,parentFunctionality);
        //代号不能重复
        assertFalse("already-exist.code", functionalityDao.exist("code", code));
        //4 kind
        int kind = po.getKind();
        // 5最后同步数据库
        SystemFunctionalityModel model = new SystemFunctionalityModel();
        model.setId(uuid());
        model.setCode(po.getCode());
        model.setCreateTime(new Date());
        model.setName(po.getName());
        model.setOrderCode(po.getOrderCode());
        model.setParentId(parentId);
        model.setRemark(po.getRemark());
        model.setStatus(po.getStatus());
        model.setKind(kind);
        model.setUrl(po.getUrl());
        this.functionalityDao.insert(model);
        return model;
    }


    @Override
    public SystemFunctionalityModel modifySystemFunctionality(String id,SystemFunctionalityPo po) throws Exception{
        id=$("id",id);
        ParameterObjectValidator.throwIfFail(po);
        //1找到旧模块
        SystemFunctionalityModel oldFunctionality = functionalityDao.select("id", id);
        assertNotNull("not-exist.id", oldFunctionality,id);

        KeyValueMap needUpdateMap = KeyValueMap.of(5);

        String newParentId = trim(po.getParentId());

        //3父功能有改变
        if(!Objects.equals(oldFunctionality.getParentId(), newParentId)){
            String parentCode=null;
            if(newParentId!=null){
                //3.1获取新父模块
                SystemFunctionalityModel newParentFunctionality = this.functionalityDao.select("id", po
                        .getParentId());
                assertNotNull("not-exist.parentId", newParentFunctionality);
                //新父模块不能是当前模块
                assertNotEquals("functionality-parentIsCurrent",newParentId,id);
                //新父模块不能是当前模块的子模块
                assertFalse("functionality-parentIsCurrentDescendant",oldFunctionality.getCode().startsWith(newParentFunctionality.getCode()));
                oldFunctionality.setParent(newParentFunctionality);
                oldFunctionality.setParentId(newParentId);
                parentCode=oldFunctionality.getCode();
            }else{
                oldFunctionality.setParent(null);
                oldFunctionality.setParentId(null);
                parentCode="";
            }
            //3.2更新父模块
            needUpdateMap.put("parentId", newParentId);

        }

        String newCode = po.getCode();
        //4代号发生改变
        if(!Objects.equals(oldFunctionality.getCode(), newCode)){
            //验证code的格式正确性
            newCode=validateFunctionalityCode(newCode,oldFunctionality.getParent());
            //4.1检查code是否存在
            assertFalse("already-exist.code", this.functionalityDao.exist("code", newCode));
            //4.2更新代号和其子功能代号
            functionalityDao.updateCodeCascade(oldFunctionality.getCode(),newCode);
            oldFunctionality.setCode(newCode);
        }



        //名称
        String newName = po.getName();
        if(!newName.equals(oldFunctionality.getName())){
            oldFunctionality.setName(newName);
            needUpdateMap.put("name", newName);
        }

        String newOrderCode = po.getOrderCode();
        if(!Objects.equals(newOrderCode, oldFunctionality.getOrderCode())){
            needUpdateMap.put("orderCode", newOrderCode);
            oldFunctionality.setOrderCode(newOrderCode);
        }

        int newStatus = po.getStatus();
        if(newStatus!=oldFunctionality.getStatus()){
            oldFunctionality.setStatus(newStatus);
            needUpdateMap.put("status", newStatus);
        }

        String newRemark = escapeTrim(po.getRemark());
        if(!Objects.equals(newRemark,oldFunctionality.getRemark())){
            oldFunctionality.setRemark(newRemark);
            needUpdateMap.put("remark", newRemark);
        }
        String newUrl = trim(po.getUrl());
        if(!Objects.equals(newUrl, oldFunctionality.getUrl())){
            oldFunctionality.setUrl(newUrl);
            needUpdateMap.put("url", newUrl);
        }

        int newKind = po.getKind();
        if(newKind!=oldFunctionality.getKind()){
            oldFunctionality.setKind(newKind);
            needUpdateMap.put("kind", newKind);
        }

        this.functionalityDao.update(id, needUpdateMap);
        return oldFunctionality;

    }


    @Override
    public void enableSystemFunctionality(String id) throws Exception{
        id = $("id", id);
        this.functionalityDao.update(id, "status", 1);
    }

    @Override
    public void disableSystemFunctionality(String id) throws Exception{
        id = $("id", id);
        this.functionalityDao.update(id, "status", 0);
    }

    @Override
    public void deleteSystemFunctionality(String id) throws Exception{
        id = $("id", id);
        SystemFunctionalityModel f=functionalityDao.select("id",id);

        assertNotNull("not-exist.id",f,id);

        //查找所有后代
        List<SystemFunctionalityModel> descendant=functionalityDao.selectDescendantByCode(f.getCode());
        //排序，保证从底层后代开始删除
        Collections.sort(descendant,(b, a)->{
            return a.getCode().length()-b.getCode().length();
        });
        for (SystemFunctionalityModel child:descendant){
            // 删除关联关系
            functionalityDao.disjoinOnRole(child.getId());
            this.functionalityDao.delete("id",child.getId());
        }
        functionalityDao.disjoinOnRole(id);
        //删除自己
        this.functionalityDao.delete("id",id);
    }

    @Override
    public SystemFunctionalityModel checkSysFunctionalityDetail(String id)
            throws Exception{
        id = $("id", id);
        SystemFunctionalityModel model = this.functionalityDao.select("id", id);
        assertNotNull("not-exist.id", model,id);
        String parentId = model.getParentId();
        if(parentId!=null){
            SystemFunctionalityModel parent = this.functionalityDao.select("id", parentId);
            model.setParent(parent);
        }
        return model;
    }

    @Override
    public List<SystemFunctionalityModel> listSystemFunctionality(String key)
            throws Exception{
        key = trim(key);
        List<SystemFunctionalityModel> list = this.functionalityDao.selectsByLike(
                        SelectLikePo.of("code,name,url",key)
                                .setOrderBy("orderCode"));
        return list;
    }
}
