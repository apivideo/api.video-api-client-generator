using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using VideoApiClient.Model;

namespace VideoApiClient.Client
{
    class EmptyArrayFixTypeConverter : JsonConverter
    {

        public override bool CanConvert(Type objectType)
        {
            return objectType == typeof(VideostatusIngest);
        }
        public override bool CanWrite => false;
        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            if (reader.TokenType == JsonToken.StartArray && !objectType.IsArray)
            {
                return null;
            }
            else
            {
                var jt = JToken.Load(reader);
                return jt.ToObject(objectType);
            }
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }
    }
}
