package com.blamejared.contenttweaker.core.api.zen.bracket;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.zen.rt.CoreMetaFactory;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.type.IParsedType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ReferenceExpression<T, U extends Reference<T>> extends ParsedExpression {
    private final ObjectType<T> objectType;
    private final TypeToken<U> referenceToken;
    private final ResourceLocation objectId;

    public ReferenceExpression(
            final CodePosition position,
            final ObjectType<T> objectType,
            final TypeToken<U> referenceToken,
            final ResourceLocation objectId
    ) {
        super(Objects.requireNonNull(position));
        this.objectType = Objects.requireNonNull(objectType);
        this.referenceToken = checkToken(Objects.requireNonNull(referenceToken));
        this.objectId = Objects.requireNonNull(objectId);
    }

    private static <R> TypeToken<R> checkToken(final TypeToken<R> token) {
        final Type type = token.getType();
        if (!(type instanceof Class<?> || type instanceof ParameterizedType)) {
            throw new IllegalStateException(token + " represents type " + type + " which is unsupported");
        }
        return token;
    }

    @Override
    public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
        final ParsedExpression metaFactory = ParseUtil.staticMemberExpression(this.position, CoreMetaFactory.ZEN_NAME);
        final ParsedExpression of = new ParsedExpressionMember(this.position, metaFactory, "reference", null);
        final ParsedCallArguments arguments = new ParsedCallArguments(this.generics(), this.arguments());
        final ParsedExpression invocation = new ParsedExpressionCall(this.position, of, arguments);
        return invocation.compile(scope);
    }

    private List<IParsedType> generics() throws CompileException {
        final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        final IZenClassRegistry classes = CraftTweakerAPI.getRegistry().getZenClassRegistry();

        final Class<?> objectType = this.objectType.type();
        final String objectName = classes.getNameFor(loader, this.objectType.type()).orElseThrow();

        final String referenceName = this.buildGenericReferenceName(loader, classes, objectType);

        final IParsedType object = BracketHelper.parseToCompile(this.position, () -> ParseUtil.readParsedType(objectName, this.position));
        final IParsedType reference = BracketHelper.parseToCompile(this.position, () -> ParseUtil.readParsedType(referenceName, this.position));

        return List.of(object, reference);
    }

    private String buildGenericReferenceName(final IScriptLoader loader, final IZenClassRegistry classes, final Class<?> objectType) {
        return buildGenericReferenceName(loader, classes, this.referenceToken.getType(), objectType);
    }

    private String buildGenericReferenceName(final IScriptLoader loader, final IZenClassRegistry classes, final Type targetType, final Class<?> objectType) {
        if (targetType instanceof ParameterizedType genericType) {
            final String rawTypeName = this.buildGenericReferenceName(loader, classes, genericType.getRawType(), objectType);
            final String generics = Arrays.stream(genericType.getActualTypeArguments())
                    .map(it -> this.buildGenericReferenceName(loader, classes, it, objectType))
                    .collect(Collectors.joining(", ", "<", ">"));
            return rawTypeName + generics;
        } else if (targetType instanceof Class<?> rawClass) {
            return classes.getNameFor(loader, rawClass).orElseThrow();
        } else if (targetType instanceof TypeVariable<?>) {
            return classes.getNameFor(loader, objectType).orElseThrow();
        } else {
            throw new IllegalStateException();
        }
    }

    private List<ParsedExpression> arguments() {
        final ParsedExpression typeId = BracketHelper.locationArgument(this.position, this.objectType.id());
        final ParsedExpression objectId = BracketHelper.locationArgument(this.position, this.objectId);
        return List.of(typeId, objectId);
    }

    @Override
    public boolean hasStrongType() {
        return true;
    }
}
